package com.winner.orderservice.order.infrastructure.client.dto.request;

import java.util.UUID;

public record CreateDeliveryRequest(
    UUID orderId,
    UUID hubId,
    UUID receiverId,
    String deliveryAddress,
    String deliveryAddressDetail
) {}

