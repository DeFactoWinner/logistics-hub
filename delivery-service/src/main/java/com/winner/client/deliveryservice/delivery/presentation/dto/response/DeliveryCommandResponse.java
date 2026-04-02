package com.winner.client.deliveryservice.delivery.presentation.dto.response;

import com.winner.client.deliveryservice.delivery.domain.entity.Delivery;
import com.winner.client.deliveryservice.delivery.domain.enums.DeliveryStatus;
import java.util.UUID;

public record DeliveryCommandResponse(
    UUID deliveryId, DeliveryStatus status) {
  public static DeliveryCommandResponse from(Delivery delivery) {
    return new DeliveryCommandResponse(
        delivery.getId(), delivery.getStatus()
    );
  }
}
