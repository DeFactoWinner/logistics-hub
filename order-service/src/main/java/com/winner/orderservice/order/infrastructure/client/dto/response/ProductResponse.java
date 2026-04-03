package com.winner.orderservice.order.infrastructure.client.dto.response;

import java.util.UUID;

public record ProductResponse(
    UUID id,
    String name,
    Long stock,
    UUID hubId
) {}

