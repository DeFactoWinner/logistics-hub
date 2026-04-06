package com.winner.client.deliveryservice.delivery.infrastructure.client.dto;

import java.util.UUID;

public record UpdateOrderInfoRequest(
    UUID delivery, UUID deliveryManagerId, String deliveryStatus
) {
}
