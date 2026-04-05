package com.winner.orderservice.order.application.dto.command;

import com.winner.orderservice.order.domain.enums.OrderStatus;
import com.winner.orderservice.order.presentation.dto.request.OrderSearchCondition;
import java.time.LocalDateTime;
import java.util.UUID;

public record SearchOrderCommand(
    OrderStatus status,
    LocalDateTime from,
    LocalDateTime to,
    UUID hubId,
    UUID companyId,
    UUID deliveryId,
    UUID assignedDeliveryPersonId,
    String sortBy,
    String sortDirection
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
        condition.sortBy(),
        condition.sortDirection()
    );
  }

  public String resolvedSortBy() {
    return (sortBy != null && !sortBy.isBlank()) ? sortBy : "createdAt";
  }

  public boolean isAscending() {
    return "asc".equalsIgnoreCase(sortDirection);
  }
}
