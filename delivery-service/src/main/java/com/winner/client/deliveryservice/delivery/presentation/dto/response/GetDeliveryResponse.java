package com.winner.client.deliveryservice.delivery.presentation.dto.response;

import com.winner.client.deliveryservice.delivery.application.dto.result.FindDeliveryResult;
import java.time.LocalDateTime;
import java.util.UUID;

public record GetDeliveryResponse(
    UUID deliveryId,
    UUID orderId,

    UUID originHubId,
    String originHubName,
    UUID destinationHubId,
    String destinationHubName,

    String status,
    String receiver,
    String slackId,
    String roadAddress,
    String detailAddress,

    String deliveryManagerName,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
  public static GetDeliveryResponse from(FindDeliveryResult query) {
    return new GetDeliveryResponse(
        query.deliveryId(),
        query.orderId(),

        query.hubRoute().getOriginHubId(),
        query.originHubName(),
        query.hubRoute().getDestinationHubId(),
        query.destinationHubName(),

        query.status().name(),

        query.receiver().getReceiver(),
        query.receiver().getSlackId(),

        query.address().getRoadAddress(),
        query.address().getDetailAddress(),

        query.deliveryManagerName(),
        query.createdAt(),
        query.updatedAt()
    );
  }
}
