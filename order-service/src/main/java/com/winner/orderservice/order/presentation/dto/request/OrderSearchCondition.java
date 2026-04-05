package com.winner.orderservice.order.presentation.dto.request;

import com.winner.orderservice.order.domain.enums.OrderStatus;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.format.annotation.DateTimeFormat;

public record OrderSearchCondition(
    OrderStatus status,

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime from,

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime to,

    UUID hubId,
    UUID companyId,
    UUID deliveryId,
    UUID assignedDeliveryPersonId,

    String sortBy,

    Direction sortDirection
) {
  public String resolvedSortBy() {
    if (sortBy == null || sortBy.isBlank()) return "createdAt";
    return switch (sortBy.trim().toLowerCase()) {
      case "updatedat" -> "updatedAt";
      case "orderedat" -> "orderedAt";
      default -> "createdAt";
    };
  }

  public boolean isAscending() {
    return Direction.ASC.equals(sortDirection);
  }
}