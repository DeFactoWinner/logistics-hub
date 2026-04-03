package com.winner.orderservice.order.application.dto.command;

import com.winner.orderservice.order.presentation.dto.request.CreateOrderRequest;
import java.time.LocalDateTime;
import java.util.UUID;

public record CreateOrderCommand(
    UUID supplierId,
    UUID receiverId,
    UUID productId,
    Long count,
    String deliveryAddress,
    String deliveryAddressDetail,
    String comment,
    LocalDateTime orderedAt
) {
  public static CreateOrderCommand from(CreateOrderRequest dto) {
    return new CreateOrderCommand(
        dto.supplierId(),
        dto.receiverId(),
        dto.productId(),
        dto.count(),
        dto.deliveryAddress(),
        dto.deliveryAddressDetail(),
        dto.comment(),
        dto.orderedAt()
    );
  }
}

