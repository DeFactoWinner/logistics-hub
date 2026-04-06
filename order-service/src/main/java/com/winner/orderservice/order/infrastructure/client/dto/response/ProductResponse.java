package com.winner.orderservice.order.infrastructure.client.dto.response;

import java.util.UUID;

public record ProductResponse(
    UUID productId,
    UUID hubId,
    UUID companyId,
    UUID stockId,
    String name,
    String description,
    String statusEnum,
    int quantity
) {}
