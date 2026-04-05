package com.winner.client.deliveryservice.delivery.application.service;

import com.winner.client.deliveryservice.delivery.domain.entity.Delivery;
import com.winner.client.deliveryservice.delivery.presentation.dto.request.UpdateDeliveryRequest;
import com.winner.client.deliveryservice.delivery.presentation.dto.response.DeliveryRouteCommandResponse;
import java.util.UUID;

public interface DeliveryRouteCommandService {
  DeliveryRouteCommandResponse updateActualDeliveryRouteInfo(
      UUID deliveryRouteId, UpdateDeliveryRequest request, UUID userId);

  DeliveryRouteCommandResponse startProgress(UUID routeId, UUID userId, String userRole, UUID referenceId);
  DeliveryRouteCommandResponse completeRoute(UUID routeId, UUID userId, String userRole, UUID referenceId);
}
