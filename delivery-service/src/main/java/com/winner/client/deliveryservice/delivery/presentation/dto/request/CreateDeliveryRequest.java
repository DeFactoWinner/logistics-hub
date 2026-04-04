package com.winner.client.deliveryservice.delivery.presentation.dto.request;

import java.util.UUID;

public record CreateDeliveryRequest(
    UUID ordersId,
    UUID originHubId,
    UUID destinationHubId,
    String originHubName,
    String destinationHubName,
    UUID receiverId,
    String receiver,
    String slackId,
    String roadAddress,
    String detailAddress
) {}
