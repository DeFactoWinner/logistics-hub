package com.winner.client.deliveryservice.common.event;

import com.winner.client.deliveryservice.delivery.domain.entity.Delivery;
import java.util.UUID;

public record AssignDeliveryManagerCompanyEvent(
    UUID deliveryId,
    UUID toHubId
) {
  public static AssignDeliveryManagerCompanyEvent from(Delivery delivery) {
    return new AssignDeliveryManagerCompanyEvent(
        delivery.getId(),
        delivery.getHubRoute().getDestinationHubId()
    );
  }
}
