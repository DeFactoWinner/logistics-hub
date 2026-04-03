package com.winner.client.deliveryservice.delivery.application.dto.result;

import com.winner.client.deliveryservice.delivery.domain.entity.Delivery;
import java.util.UUID;

public record CreateDeliveryResult(
    UUID deliveryId, UUID deliveryManagerId
) {
  public static CreateDeliveryResult from(Delivery delivery){
    return new CreateDeliveryResult(
        delivery.getId(),
        delivery.getDeliveryManagerId()
    );
  }
}
