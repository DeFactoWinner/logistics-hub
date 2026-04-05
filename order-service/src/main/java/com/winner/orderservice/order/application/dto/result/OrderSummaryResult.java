package com.winner.orderservice.order.application.dto.result;

import com.winner.orderservice.order.domain.entity.Order;
import com.winner.orderservice.order.domain.enums.OrderStatus;
import java.time.LocalDateTime;
import java.util.UUID;

public record OrderSummaryResult(
    UUID orderId,
    UUID hubId,
    UUID supplierId,
    UUID receiverId,
    UUID deliveryId,
    UUID productId,
    String productName,
    Long count,
    OrderStatus status,
    LocalDateTime orderedAt,
    LocalDateTime createdAt
) {
  public static OrderSummaryResult from(Order order) {
    return new OrderSummaryResult(
        order.getId(),
        order.getHubId(),
        order.getParticipants().getSupplierId(),
        order.getParticipants().getReceiverId(),
        order.getDeliveryId(),
        order.getOrderDetail().getProductId(),
        order.getSnapshot().getProductName(),
        order.getOrderDetail().getCount(),
        order.getStatus(),
        order.getOrderedAt(),
        order.getCreatedAt()
    );
  }
}
