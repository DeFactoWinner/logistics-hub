package com.winner.client.deliveryservice.common.event;

import com.winner.client.deliveryservice.delivery.domain.entity.Delivery;
import com.winner.client.deliveryservice.delivery.domain.entity.DeliveryRoute;
import java.util.UUID;

public record AssignDeliveryManagerHubEvent(
    UUID deliveryId,
    UUID deliveryRouteId
) {
  public static AssignDeliveryManagerHubEvent of(Delivery delivery, DeliveryRoute firstRoute) {
    return new AssignDeliveryManagerHubEvent(
        delivery.getId(),
        firstRoute.getId()
    );
  }

  public static AssignDeliveryManagerHubEvent of(DeliveryRoute firstRoute) {
    return new AssignDeliveryManagerHubEvent(
        firstRoute.getDelivery().getId(),
        firstRoute.getId()
    );
  }
}
