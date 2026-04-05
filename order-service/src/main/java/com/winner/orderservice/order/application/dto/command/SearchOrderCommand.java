package com.winner.orderservice.order.application.dto.command;

import com.winner.orderservice.order.domain.enums.OrderStatus;
import com.winner.orderservice.order.presentation.dto.request.OrderSearchCondition;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.data.domain.Sort.Direction;

public record SearchOrderCommand(
    OrderStatus status,
    LocalDateTime from,
    LocalDateTime to,
    UUID hubId,
    UUID companyId,
    UUID deliveryId,
    UUID assignedDeliveryPersonId,
    String sortBy,
    Direction sortDirection
) {
  public static SearchOrderCommand from(OrderSearchCondition condition) {
    return new SearchOrderCommand(
        condition.status(),
        condition.from(),
        condition.to(),
        condition.hubId(),
        condition.companyId(),
        condition.deliveryId(),
        condition.assignedDeliveryPersonId(),
        condition.resolvedSortBy(),
        condition.isAscending() ? Direction.ASC : Direction.DESC
    );
  }

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