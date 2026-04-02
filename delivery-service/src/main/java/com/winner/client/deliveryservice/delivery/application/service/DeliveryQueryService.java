package com.winner.client.deliveryservice.delivery.application.service;

import com.winner.client.deliveryservice.delivery.presentation.dto.response.GetDeliveryResponse;
import com.winner.client.deliveryservice.delivery.presentation.dto.response.ListDeliveryRouteResponse;
import java.util.UUID;

public interface DeliveryQueryService {
  GetDeliveryResponse getDeliveryDetail(UUID deliveryId);
  ListDeliveryRouteResponse getDeliveryRoutes(UUID deliveryId);
}
