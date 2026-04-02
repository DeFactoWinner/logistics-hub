package com.winner.client.deliveryservice.delivery.presentation.dto.response;

import com.winner.client.deliveryservice.delivery.domain.entity.Delivery;
import com.winner.client.deliveryservice.delivery.domain.enums.DeliveryStatus;
import java.time.LocalDateTime;
import java.util.UUID;

public record GetDeliveryResponse(
    UUID deliveryId, UUID orderId,
    HubInfo originHubInfo, HubInfo destinationHubInfo,
    DeliveryStatus deliveryStatus,
    String receiver, String slackId,
    String roadAddress, String detailAddress,
    UUID deliveryManagerId,
    LocalDateTime createdAt, LocalDateTime updatedAt
) {
  public static GetDeliveryResponse from(Delivery delivery) {
    return new GetDeliveryResponse(
        delivery.getId(),
        delivery.getOrdersId(),
        new HubInfo(delivery.getHubRoute().getOriginHubId(), delivery.getOriginHubName()),
        new HubInfo(delivery.getHubRoute().getDestinationHubId(), delivery.getDestinationHubName()),
        delivery.getStatus(),
        delivery.getReceiver().getReceiver(),
        delivery.getReceiver().getSlackId(),
        delivery.getAddress().getRoadAddress(),
        delivery.getAddress().getDetailAddress(),
        delivery.getDeliveryManagerId(),
        delivery.getCreatedAt(),
        delivery.getUpdatedAt()
    );
  }
}
