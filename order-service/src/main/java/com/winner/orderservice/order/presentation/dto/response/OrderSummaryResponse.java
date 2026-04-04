package com.winner.orderservice.order.presentation.dto.response;

import com.winner.orderservice.order.domain.entity.Order;
import com.winner.orderservice.order.domain.enums.OrderStatus;
import com.winner.orderservice.order.application.dto.result.OrderSummaryResult;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record OrderSummaryResponse(
    UUID id,
    UUID supplierId,
    UUID receiverId,
    UUID productId,
    String productName,
    Long count,
    OrderStatus status,
    UUID deliveryId,
    UUID hubId,
    LocalDateTime orderedAt,
    LocalDateTime createdAt
) {
  public static OrderSummaryResponse from(Order order) {
    return OrderSummaryResponse.builder()
        .id(order.getId())
        .supplierId(order.getParticipants().getSupplierId())
        .receiverId(order.getParticipants().getReceiverId())
        .productId(order.getOrderDetail().getProductId())
        .productName(order.getSnapshot().getProductName())
        .count(order.getOrderDetail().getCount())
        .status(order.getStatus())
        .deliveryId(order.getDeliveryId())
        .hubId(order.getHubId())
        .orderedAt(order.getOrderedAt())
        .createdAt(order.getCreatedAt())
        .build();
  }

  public static OrderSummaryResponse fromResult(OrderSummaryResult result) {
    return OrderSummaryResponse.builder()
        .id(result.orderId())
        .supplierId(result.supplierId())
        .receiverId(result.receiverId())
        .productId(result.productId())
        .count(result.count())
        .productName(result.productName())
        .status(result.status())
        .deliveryId(result.deliveryId())
        .hubId(result.hubId())
        .orderedAt(result.orderedAt())
        .build();
  }
}
