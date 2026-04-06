package com.winner.client.deliveryservice.delivery.application.service.impl;

import com.winner.client.deliveryservice.common.exception.delivery.DeliveryErrorCode;
import com.winner.client.deliveryservice.delivery.application.dto.command.CompanyDeliveryManagerAssignFailCommand;
import com.winner.client.deliveryservice.delivery.application.dto.command.CompleteDeliveryCommand;
import com.winner.client.deliveryservice.delivery.application.dto.command.CompleteDeliveryRouteCommand;
import com.winner.client.deliveryservice.delivery.application.dto.command.DeliveryAssignCompleteCommand;
import com.winner.client.deliveryservice.delivery.application.dto.command.DeliveryRouteAssignCompleteCommand;
import com.winner.client.deliveryservice.delivery.application.dto.command.HubDeliveryManagerAssignFailCommand;
import com.winner.client.deliveryservice.delivery.application.port.OrderPort;
import com.winner.client.deliveryservice.delivery.application.service.DeliveryAssignmentService;
import com.winner.client.deliveryservice.delivery.application.service.DeliveryMessageUsecase;
import com.winner.client.deliveryservice.delivery.domain.entity.Delivery;
import com.winner.client.deliveryservice.delivery.domain.entity.DeliveryRoute;
import com.winner.client.deliveryservice.delivery.domain.repository.DeliveryRepository;
import com.winner.client.deliveryservice.delivery.domain.repository.DeliveryRouteRepository;
import com.winner.client.global.exception.BusinessException;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryMessageServiceImpl implements DeliveryMessageUsecase {

  private final DeliveryRepository deliveryRepository;
  private final DeliveryRouteRepository deliveryRouteRepository;
  private final DeliveryAssignmentService deliveryAssignmentService;
  private final OrderPort orderPort;
  private final TaskScheduler taskScheduler;
  private final Set<UUID> retryingDeliveries = ConcurrentHashMap.newKeySet();

  @Override
  @Transactional
  public void completeHubDeliveryManagerAssign(DeliveryRouteAssignCompleteCommand command) {
    DeliveryRoute route = deliveryRouteRepository.findFirstWaitingRoute(command.deliveryId())
        .orElseThrow(() -> new BusinessException(DeliveryErrorCode.NOT_FOUND_DELIVERY_ROUTE));

    route.updateDeliveryManagerInfo(command.deliveryManagerId(), command.deliveryManagerName());
    route.startProgress();

    Delivery delivery = route.getDelivery();
    if (route.isFirstRoute()) {
      delivery.startHubMoving();
    }

    try {
      orderPort.updateOrderDeliveryInfo(
          delivery.getOrdersId(),
          command.deliveryManagerId()
      );
      log.info("주문 서비스에 배송 매니저 정보 업데이트 완료. OrdersId: {}", delivery.getOrdersId());
    } catch (Exception e) {
      log.error("주문 서비스 업데이트 중 오류 발생: {}", e.getMessage(), e);
      // todo: 실패 시 나중에 재시도하기 위해 별도의 기록(DB/Queue)을 남길 수 있습니다.
    }
  }

  @Override
  @Transactional
  public void completeCompanyDeliveryManagerAssign(DeliveryAssignCompleteCommand command) {
    Delivery delivery = findDeliveryById(command.deliveryId());

    delivery.updateDeliveryManagerInfo(command.deliveryManagerId(), command.deliveryManagerName());
    delivery.startVendorMoving();

    try {
      orderPort.updateOrderDeliveryInfo(
          delivery.getOrdersId(),
          command.deliveryManagerId()
      );
      log.info("주문 서비스에 배송 매니저 정보 업데이트 완료. OrdersId: {}", delivery.getOrdersId());
    } catch (Exception e) {
      log.error("주문 서비스 업데이트 중 오류 발생: {}", e.getMessage(), e);
      // todo: 실패 시 나중에 재시도하기 위해 별도의 기록(DB/Queue)을 남길 수 있습니다.
    }
  }

  @Override
  @Transactional
  public void completeDeliveryRoute(CompleteDeliveryRouteCommand command) {
    DeliveryRoute route = deliveryRouteRepository.findFirstInProgressRoute(command.deliveryId())
        .orElseThrow(() -> new BusinessException(DeliveryErrorCode.NOT_FOUND_DELIVERY_ROUTE));

    route.complete();

    Optional<DeliveryRoute> nextPending = deliveryRouteRepository.findFirstWaitingRoute(command.deliveryId());

    if (nextPending.isPresent()) {
      deliveryAssignmentService.retryHubDeliveryManager(command.deliveryId());
    } else {
      Delivery delivery = route.getDelivery();
      deliveryAssignmentService.assignCompanyDeliveryManager(delivery);
    }
  }

  @Override
  @Transactional
  public void completeDelivery(CompleteDeliveryCommand command) {
    Delivery delivery = findDeliveryById(command.deliveryId());
    delivery.complete();

    try {
      orderPort.updateOrderDeliveryCompleted(delivery.getOrdersId());
      log.info("주문 상태 완료 처리 성공. OrdersId: {}", delivery.getOrdersId());
    } catch (Exception e) {
      log.error("주문 상태 처리 중 오류 발생: {}", e.getMessage(), e);
      // todo: 실패 시 나중에 재시도하기 위해 별도의 기록(DB/Queue)을 남길 수 있습니다.
    }
  }

  @Override
  @Transactional
  public void retryHubDeliveryManagerAssign(HubDeliveryManagerAssignFailCommand command) {
    UUID deliveryId = command.deliveryId();

    if (!retryingDeliveries.add(deliveryId)) {
      log.warn("[중복 retry 무시 - HUB] deliveryId={}", deliveryId); return;
    }

    log.info("[재시도 예약 - HUB] deliveryId={}, 10분 후 실행", deliveryId);

    taskScheduler.schedule(
        () -> {
          try {
            log.info("[재시도 실행 - HUB] deliveryId={}", deliveryId);
            deliveryAssignmentService.retryHubDeliveryManager(deliveryId);
          } finally {
            retryingDeliveries.remove(deliveryId);
          }
        },
        Instant.now().plusSeconds(600)
    );
  }

  @Override
  @Transactional
  public void retryCompanyDeliveryManagerAssign(CompanyDeliveryManagerAssignFailCommand command) {
    UUID deliveryId = command.deliveryId();

    if (!retryingDeliveries.add(deliveryId)) {
      log.warn("[중복 retry 무시 - COMPANY] deliveryId={}", deliveryId); return;
    }

    log.info("[재시도 예약 - COMPANY] deliveryId={}, 10분 후 실행", deliveryId);

    taskScheduler.schedule(
        () -> {
          try {
            log.info("[재시도 실행 - COMPANY] deliveryId={}", deliveryId);

            Delivery delivery = findDeliveryById(deliveryId);
            deliveryAssignmentService.assignCompanyDeliveryManager(delivery);
          } finally {
            retryingDeliveries.remove(deliveryId);
          }
        },
        Instant.now().plusSeconds(600)
    );
  }

  private Delivery findDeliveryById(UUID deliveryId) {
    return deliveryRepository.findById(deliveryId)
        .orElseThrow(()-> new BusinessException(DeliveryErrorCode.NOT_FOUND_DELIVERY));
  }

}
