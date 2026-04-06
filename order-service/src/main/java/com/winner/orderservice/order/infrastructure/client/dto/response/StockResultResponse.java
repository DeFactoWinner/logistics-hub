package com.winner.orderservice.order.infrastructure.client.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record StockResultResponse(
    UUID stockId,
    UUID productId,
    Object quantity,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}
