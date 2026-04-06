package com.winner.orderservice.order.infrastructure.client.dto.response;

import java.util.UUID;

public record DeliveryResponse(
    UUID deliveryId,
    UUID deliveriesId // delivery-service의 필드명과 동일해야 함
) {}