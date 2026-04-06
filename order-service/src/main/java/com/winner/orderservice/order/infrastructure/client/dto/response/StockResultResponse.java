package com.winner.orderservice.order.infrastructure.client.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record StockResultResponse(
    UUID stockId,
    UUID productId,
    Quantity quantity, // Object 대신 전용 DTO 사용
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
  public record Quantity(
      int value
  ) {}
}
