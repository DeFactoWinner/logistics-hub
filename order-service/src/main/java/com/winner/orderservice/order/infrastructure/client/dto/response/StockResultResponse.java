package com.winner.orderservice.order.infrastructure.client.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record StockResultResponse(
    UUID stockId,
    UUID productId,
    Object quantity, // 필요에 따라 order-service에 맞는 Quantity 타입으로 변경
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}
