package com.winner.client.deliveryservice.delivery.application.service.impl;

import com.winner.client.deliveryservice.common.exception.delivery.DeliveryErrorCode;
import com.winner.client.deliveryservice.delivery.application.service.DeliveryRouteCommandService;
import com.winner.client.deliveryservice.delivery.application.validator.DeliveryAccessValidator;
import com.winner.client.deliveryservice.delivery.domain.entity.DeliveryRoute;
import com.winner.client.deliveryservice.delivery.domain.repository.DeliveryRouteRepository;
import com.winner.client.deliveryservice.delivery.presentation.dto.request.UpdateDeliveryRequest;
import com.winner.client.deliveryservice.delivery.presentation.dto.response.DeliveryRouteCommandResponse;
import com.winner.client.global.exception.BusinessException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DeliveryRouteCommandServiceImpl implements DeliveryRouteCommandService {
  private final DeliveryRouteRepository deliveryRouteRepository;
  private final DeliveryAccessValidator validator;

  @Override
  public DeliveryRouteCommandResponse updateActualDeliveryRouteInfo(
      UUID deliveryRouteId, UpdateDeliveryRequest request, UUID userId) {
    DeliveryRoute route = findById(deliveryRouteId);
    route.updateActualDeliveryRouteInfo(request.actualDistance(), request.actualArrivalTime());

    return DeliveryRouteCommandResponse.from(route);
  }

  @Override
  public DeliveryRouteCommandResponse assignRoute(UUID routeId, String userRole, UUID referenceId) {
    DeliveryRoute route = findById(routeId);
    validator.validateRouteHubAdminAccess(route, userRole, referenceId);
    route.assign();
    return DeliveryRouteCommandResponse.from(route);
  }

  @Override
  public DeliveryRouteCommandResponse startProgress(UUID routeId, UUID userId, String userRole, UUID referenceId) {
    DeliveryRoute route = findById(routeId);
    validator.validateRouteDeliveryManagerAccess(route, userId, userRole, referenceId);
    route.startProgress();
    return DeliveryRouteCommandResponse.from(route);
  }

  @Override
  public DeliveryRouteCommandResponse completeRoute(UUID routeId, UUID userId, String userRole, UUID referenceId) {
    DeliveryRoute route = findById(routeId);
    validator.validateRouteDeliveryManagerAccess(route, userId, userRole, referenceId);
    route.complete();
    return DeliveryRouteCommandResponse.from(route);
  }

  private DeliveryRoute findById(UUID deliveryRouteId) {
    return deliveryRouteRepository.findById(deliveryRouteId)
        .orElseThrow(()-> new BusinessException(DeliveryErrorCode.NOT_FOUND_DELIVERY_ROUTE));
  }
}
