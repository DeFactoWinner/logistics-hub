package com.winner.client.deliveryservice.delivery.presentation.dto.response;

import com.winner.client.deliveryservice.delivery.application.dto.result.SearchDeliveryResult;
import java.util.UUID;

public record DeliveryInfoResponse(
    UUID deliveryId,

    String originHubName,
    String destinationHubName,

    String status,
    String receiver,
    String deliveryManagerName
) {
  public static DeliveryInfoResponse from(SearchDeliveryResult result) {
    return new DeliveryInfoResponse(
        result.id(),
        result.originHubName(),
        result.destinationHubName(),
        result.status().name(),
        result.receiver().getReceiver(),
        result.deliveryManagerName()
    );
  }
}
