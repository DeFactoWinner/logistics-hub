package com.winner.client.deliveryservice.delivery.application.dto.result;

import com.winner.client.deliveryservice.delivery.domain.entity.Delivery;
import com.winner.client.deliveryservice.delivery.domain.enums.DeliveryStatus;
import com.winner.client.deliveryservice.delivery.domain.vo.Address;
import com.winner.client.deliveryservice.delivery.domain.vo.HubRoute;
import com.winner.client.deliveryservice.delivery.domain.vo.Receiver;
import java.time.LocalDateTime;
import java.util.UUID;

public record FindDeliveryResult(
    UUID deliveryId,
    UUID orderId,

    HubRoute hubRoute,
    String originHubName,
    String destinationHubName,
    DeliveryStatus status,

    Receiver receiver,
    Address address,

    String deliveryManagerName,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
  public static FindDeliveryResult from(Delivery delivery) {
    return new FindDeliveryResult(
        delivery.getId(),
        delivery.getOrdersId(),
        delivery.getHubRoute(),
        delivery.getOriginHubName(),
        delivery.getDestinationHubName(),
        delivery.getStatus(),
        delivery.getReceiver(),
        delivery.getAddress(),
        delivery.getDeliveryManagerName(),
        delivery.getCreatedAt(),
        delivery.getUpdatedAt()
    );
  }
}