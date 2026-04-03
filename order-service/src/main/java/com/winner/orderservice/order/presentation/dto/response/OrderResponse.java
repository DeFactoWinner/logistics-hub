package com.winner.orderservice.order.presentation.dto.response;

import com.winner.orderservice.order.domain.entity.Order;
import com.winner.orderservice.order.domain.enums.OrderStatus;
import com.winner.orderservice.order.application.dto.result.OrderResult;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record OrderResponse(
    UUID id,
    UUID supplierId,
    UUID receiverId,
    UUID productId,
    Long count,
    String comment,
    String productName,
    String deliveryAddress,
    String deliveryAddressDetail,
    OrderStatus status,
    UUID deliveryId,
    UUID hubId,
    LocalDateTime orderedAt,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
  public static OrderResponse from(Order order) {
    return OrderResponse.builder()
        .id(order.getId())
        .supplierId(order.getParticipants().getSupplierId())
        .receiverId(order.getParticipants().getReceiverId())
        .productId(order.getOrderDetail().getProductId())
        .count(order.getOrderDetail().getCount())
        .comment(order.getOrderDetail().getComment())
        .productName(order.getSnapshot().getProductName())
        .deliveryAddress(order.getSnapshot().getDeliveryAddress())
        .deliveryAddressDetail(order.getSnapshot().getDeliveryAddressDetail())
        .status(order.getStatus())
        .deliveryId(order.getDeliveryId())
        .hubId(order.getHubId())
        .orderedAt(order.getOrderedAt())
        .createdAt(order.getCreatedAt())
        .updatedAt(order.getUpdatedAt())
        .build();
  }

  public static OrderResponse fromResult(OrderResult result) {
    return OrderResponse.builder()
        .id(result.orderId())
        .hubId(result.hubId())
        .supplierId(result.supplierId())
        .receiverId(result.receiverId())
        .deliveryId(result.deliveryId())
        .productId(result.productId())
        .productName(result.productName())
        .count(result.count())
        .deliveryAddress(result.deliveryAddress())
        .deliveryAddressDetail(result.deliveryAddressDetail())
        .comment(result.comment())
        .status(result.status())
        .orderedAt(result.orderedAt())
        .build();
  }
}
