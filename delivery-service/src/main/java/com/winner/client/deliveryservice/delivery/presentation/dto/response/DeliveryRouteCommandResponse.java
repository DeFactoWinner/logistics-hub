package com.winner.client.deliveryservice.delivery.presentation.dto.response;

import com.winner.client.deliveryservice.delivery.domain.entity.DeliveryRoute;
import com.winner.client.deliveryservice.delivery.domain.enums.DeliveryRouteStatus;
import java.util.UUID;

public record DeliveryRouteCommandResponse(
    UUID deliveryRouteId, UUID deliveryId, DeliveryRouteStatus status) {
  public static DeliveryRouteCommandResponse from(DeliveryRoute route) {
    return new DeliveryRouteCommandResponse(
        route.getId(),
        route.getDeliveryId(),
        route.getStatus()
    );
  }
}
