package com.winner.client.deliveryservice.delivery.application.service.impl;

import com.winner.client.deliveryservice.common.exception.delivery.DeliveryErrorCode;
import com.winner.client.deliveryservice.delivery.application.dto.command.CompanyDeliveryManagerAssignFailCommand;
import com.winner.client.deliveryservice.delivery.application.dto.command.DeliveryAssignCompleteCommand;
import com.winner.client.deliveryservice.delivery.application.dto.command.DeliveryRouteAssignCompleteCommand;
import com.winner.client.deliveryservice.delivery.application.dto.command.HubDeliveryManagerAssignFailCommand;
import com.winner.client.deliveryservice.delivery.application.service.DeliveryMessageUsecase;
import com.winner.client.deliveryservice.delivery.application.service.DeliveryAssignmentService;
import com.winner.client.deliveryservice.delivery.domain.entity.Delivery;
import com.winner.client.deliveryservice.delivery.domain.entity.DeliveryRoute;
import com.winner.client.deliveryservice.delivery.domain.repository.DeliveryRepository;
import com.winner.client.deliveryservice.delivery.domain.repository.DeliveryRouteRepository;
import com.winner.client.global.exception.BusinessException;
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

  @Override
  @Transactional
  public void completeHubDeliveryManagerAssign(DeliveryRouteAssignCompleteCommand command) {
    DeliveryRoute route = findDeliveryRouteById(command.deliveryRouteId());
    route.updateDeliveryManagerInfo(command.deliveryManagerId(), command.deliveryManagerName());
    // todo: order 배송 담당자 정보 update
  }

  @Override
  @Transactional
  public void completeCompanyDeliveryManagerAssign(DeliveryAssignCompleteCommand command) {
    Delivery delivery = findDeliveryById(command.deliveryId());

    delivery.updateDeliveryManagerInfo(command.deliveryManagerId(), command.deliveryManagerName());
    // todo: order 배송 담당자 정보 update
  }

  @Override
  public void retryHubDeliveryManagerAssign(HubDeliveryManagerAssignFailCommand command) {
    DeliveryRoute failedRoute = findDeliveryRouteById(command.deliveryRouteId());

    deliveryAssignmentService.retryHubDeliveryManager(failedRoute);
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

  private DeliveryRoute findDeliveryRouteById(UUID deliveryId) {
    return deliveryRouteRepository.findById(deliveryId)
        .orElseThrow(() -> new BusinessException(DeliveryErrorCode.NOT_FOUND_DELIVERY_ROUTE));

  }

}
