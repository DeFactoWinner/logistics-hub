package com.winner.client.deliveryservice.delivery.application.service.impl;

import com.winner.client.deliveryservice.common.exception.delivery.DeliveryErrorCode;
import com.winner.client.deliveryservice.delivery.application.dto.command.CreateDeliveryCommand;
import com.winner.client.deliveryservice.delivery.application.dto.command.CreateDeliveryRouteCommand;
import com.winner.client.deliveryservice.delivery.application.dto.external.HubRouteInfo;
import com.winner.client.deliveryservice.delivery.application.dto.result.CreateDeliveryResult;
import com.winner.client.deliveryservice.delivery.application.reader.HubRouteReader;
import com.winner.client.deliveryservice.delivery.application.service.DeliveryAssignmentService;
import com.winner.client.deliveryservice.delivery.application.service.DeliveryCommandService;
import com.winner.client.deliveryservice.delivery.application.validator.DeliveryAccessValidator;
import com.winner.client.deliveryservice.delivery.application.validator.UserRole;
import com.winner.client.deliveryservice.delivery.domain.entity.Delivery;
import com.winner.client.deliveryservice.delivery.domain.entity.DeliveryRoute;
import com.winner.client.deliveryservice.delivery.domain.repository.DeliveryRepository;
import com.winner.client.deliveryservice.delivery.presentation.dto.response.DeliveryCommandResponse;
import com.winner.client.global.exception.BusinessException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeliveryCommandServiceImpl implements DeliveryCommandService {

  private final DeliveryRepository deliveryRepository;
  private final DeliveryAssignmentService deliveryAssignmentService;
  private final DeliveryAccessValidator validator;
  private final HubRouteReader hubRouteReader;

  @Override
  public CreateDeliveryResult createDelivery(CreateDeliveryCommand command) {
    if(deliveryRepository.findByOrdersId(command.ordersId())){
      throw new BusinessException(DeliveryErrorCode.ALREADY_DELIVERY_ASSIGNED);
    }

    Delivery delivery = Delivery.create(
        command.ordersId(), command.hubRoute(), command.originHubName(), command.destinationHubName(),
        command.receiver(), command.address(), null);

    if (delivery.isSameHub()) {
      deliveryAssignmentService.assignCompanyDeliveryManager(delivery);
    } else {
      List<DeliveryRoute> routes = prepareHubRoutes(delivery);
      deliveryAssignmentService.assignHubDeliveryManager(delivery, routes);
    }

    return CreateDeliveryResult.from(delivery);
  }

  private List<DeliveryRoute> prepareHubRoutes(Delivery delivery) {
    HubRouteInfo hubRouteInfo = hubRouteReader.getHubRoutes(
        delivery.getHubRoute().getOriginHubId(),
        delivery.getHubRoute().getDestinationHubId()
    );
    return CreateDeliveryRouteCommand.of(delivery, hubRouteInfo)
        .stream()
        .map(DeliveryRoute::create)
        .toList();
  }

  @Override
  @Transactional
  public DeliveryCommandResponse startHubMoving(
      UUID deliveryId, String userRole, UUID referenceId) {
    Delivery delivery = findById(deliveryId);
    validator.validateHubAdminAccess(delivery, userRole, referenceId);
    delivery.startHubMoving();
    return DeliveryCommandResponse.from(delivery);
  }

  @Override
  @Transactional
  public DeliveryCommandResponse arriveDestination(
      UUID deliveryId, String userRole, UUID referenceId) {
    Delivery delivery = findByIdWithRoutes(deliveryId);
    validator.validateHubAdminAccess(delivery, userRole, referenceId);
    delivery.arriveDestination();
    return DeliveryCommandResponse.from(delivery);
  }

  @Override
  @Transactional
  public DeliveryCommandResponse startVendorMoving(
      UUID deliveryId, UUID userId, String userRole, UUID referenceId) {
    Delivery delivery = findById(deliveryId);
    validator.validateDeliveryManagerAccess(delivery, userId, userRole, referenceId);
    delivery.startVendorMoving();
    return DeliveryCommandResponse.from(delivery);
  }

  @Override
  @Transactional
  public DeliveryCommandResponse completeDelivery(
      UUID deliveryId, UUID userId, String userRole, UUID referenceId) {
    Delivery delivery = findById(deliveryId);
    validator.validateDeliveryManagerAccess(delivery, userId, userRole, referenceId);
    delivery.complete();
    return DeliveryCommandResponse.from(delivery);
  }

  @Override
  @Transactional
  public DeliveryCommandResponse cancelDelivery(
      UUID deliveryId, String userRole) {
    validator.validateRole(userRole, UserRole.MASTER);
    Delivery delivery = findByIdWithRoutes(deliveryId);
    delivery.cancel();
    return DeliveryCommandResponse.from(delivery);
  }

  private Delivery findById(UUID deliveryId) {
    return deliveryRepository.findById(deliveryId)
        .orElseThrow(()-> new BusinessException(DeliveryErrorCode.NOT_FOUND_DELIVERY));
  }

  private Delivery findByIdWithRoutes(UUID deliveryId) {
    return deliveryRepository.findByIdWithRoutes(deliveryId)
        .orElseThrow(()-> new BusinessException(DeliveryErrorCode.NOT_FOUND_DELIVERY));
  }
}
