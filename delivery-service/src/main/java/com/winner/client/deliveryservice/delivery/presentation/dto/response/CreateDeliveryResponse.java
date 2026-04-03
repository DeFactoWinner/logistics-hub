package com.winner.client.deliveryservice.delivery.presentation.dto.response;

import com.winner.client.deliveryservice.delivery.application.dto.result.CreateDeliveryResult;
import java.util.UUID;

public record CreateDeliveryResponse(UUID deliveryId, UUID deliveriesId) {
  public static CreateDeliveryResponse from(CreateDeliveryResult result) {
    return new CreateDeliveryResponse(
        result.deliveryId(),
        result.deliveryManagerId()
    );
  }
}
