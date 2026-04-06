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
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeliveryMessageServiceImpl implements DeliveryMessageUsecase {

  private final DeliveryRepository deliveryRepository;
  private final DeliveryRouteRepository deliveryRouteRepository;
  private final DeliveryAssignmentService deliveryAssignmentService;
  private final OrderPort orderPort;

  @Override
  @Transactional
  public void completeHubDeliveryManagerAssign(DeliveryRouteAssignCompleteCommand command) {
    DeliveryRoute route = deliveryRouteRepository.findFirstPendingRoute(command.deliveryId())
        .orElseThrow(() -> new BusinessException(DeliveryErrorCode.NOT_FOUND_DELIVERY_ROUTE));

    route.updateDeliveryManagerInfo(command.deliveryManagerId(), command.deliveryManagerName());
    route.startProgress();

    Delivery delivery = route.getDelivery();

    if (route.isFirstRoute()) {
      delivery.startHubMoving();
    }

    orderPort.updateOrderDeliveryInfo(
        delivery.getOrdersId(),
        command.deliveryManagerId()
    );
  }

  @Override
  @Transactional
  public void completeCompanyDeliveryManagerAssign(DeliveryAssignCompleteCommand command) {
    Delivery delivery = findDeliveryById(command.deliveryId());

    delivery.updateDeliveryManagerInfo(command.deliveryManagerId(), command.deliveryManagerName());
    delivery.startVendorMoving();

    orderPort.updateOrderDeliveryInfo(
        delivery.getOrdersId(),
        command.deliveryManagerId()
    );
  }

  @Override
  @Transactional
  public void completeDeliveryRoute(CompleteDeliveryRouteCommand command) {
    DeliveryRoute route = deliveryRouteRepository.findFirstInProgressRoute(command.deliveryId())
        .orElseThrow(() -> new BusinessException(DeliveryErrorCode.NOT_FOUND_DELIVERY_ROUTE));

    route.complete();

    Optional<DeliveryRoute> nextPending = deliveryRouteRepository.findFirstPendingRoute(command.deliveryId());

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

    orderPort.updateOrderDeliveryCompleted(delivery.getOrdersId());
  }

  @Override
  public void retryHubDeliveryManagerAssign(HubDeliveryManagerAssignFailCommand command) {
    deliveryAssignmentService.retryHubDeliveryManager(command.deliveryId());
  }

  @Override
  public void retryCompanyDeliveryManagerAssign(CompanyDeliveryManagerAssignFailCommand command) {
    Delivery delivery = findDeliveryById(command.deliveryId());
    deliveryAssignmentService.assignCompanyDeliveryManager(delivery);
  }

  private Delivery findDeliveryById(UUID deliveryId) {
    return deliveryRepository.findById(deliveryId)
        .orElseThrow(()-> new BusinessException(DeliveryErrorCode.NOT_FOUND_DELIVERY));
  }

}
