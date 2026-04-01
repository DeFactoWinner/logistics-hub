package com.winner.client.deliveryservice.delivery.application.service;

import com.winner.client.deliveryservice.delivery.presentation.dto.request.UpdateDeliveryRequest;
import com.winner.client.deliveryservice.delivery.presentation.dto.response.DeliveryRouteCommandResponse;
import java.util.UUID;

public interface DeliveryCommendService {
  DeliveryRouteCommandResponse updateActualDeliveryRouteInfo(
      UUID deliveryRouteId, UpdateDeliveryRequest request, UUID userId);
}
