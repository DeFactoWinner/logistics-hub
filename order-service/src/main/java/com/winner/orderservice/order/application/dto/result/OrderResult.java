package com.winner.orderservice.order.application.dto.result;

import com.winner.orderservice.order.domain.entity.Order;
import com.winner.orderservice.order.domain.enums.OrderStatus;
import java.time.LocalDateTime;
import java.util.UUID;

public record OrderResult(
    UUID orderId,
    UUID hubId,
    UUID supplierId,
    UUID receiverId,
    UUID deliveryId,
    UUID productId,
    String productName,
    Long count,
    String deliveryAddress,
    String deliveryAddressDetail,
    String comment,
    OrderStatus status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    LocalDateTime orderedAt
) {
  public static OrderResult from(Order order) {
    return new OrderResult(
        order.getId(),
        order.getHubId(),
        order.getParticipants().getSupplierId(),
        order.getParticipants().getReceiverId(),
        order.getDeliveryId(),
        order.getOrderDetail().getProductId(),
        order.getSnapshot().getProductName(),
        order.getOrderDetail().getCount(),
        order.getSnapshot().getDeliveryAddress(),
        order.getSnapshot().getDeliveryAddressDetail(),
        order.getOrderDetail().getComment(),
        order.getStatus(),
        order.getCreatedAt(),
        order.getUpdatedAt(),
        order.getOrderedAt()
    );
  }
}
