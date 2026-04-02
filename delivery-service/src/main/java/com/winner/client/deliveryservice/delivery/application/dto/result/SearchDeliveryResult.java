package com.winner.client.deliveryservice.delivery.application.dto.result;

import com.winner.client.deliveryservice.delivery.domain.entity.Delivery;
import com.winner.client.deliveryservice.delivery.domain.enums.DeliveryStatus;
import com.winner.client.deliveryservice.delivery.domain.vo.Receiver;
import java.util.UUID;

public record SearchDeliveryResult(
    UUID id,
    String originHubName,
    String destinationHubName,
    Receiver receiver,
    DeliveryStatus status,
    String deliveryManagerName
) {
  public static SearchDeliveryResult from(Delivery delivery) {
    return new SearchDeliveryResult(
        delivery.getId(),
        delivery.getOriginHubName(),
        delivery.getDestinationHubName(),
        delivery.getReceiver(),
        delivery.getStatus(),
        delivery.getDeliveryManagerName()
    );
  }
}
