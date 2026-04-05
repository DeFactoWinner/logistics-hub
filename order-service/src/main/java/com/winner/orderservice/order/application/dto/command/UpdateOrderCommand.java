package com.winner.orderservice.order.application.dto.command;

import com.winner.orderservice.order.presentation.dto.request.UpdateOrderRequest;

public record UpdateOrderCommand(
    Long count,
    String comment
) {
  public static UpdateOrderCommand from(UpdateOrderRequest dto) {
    return new UpdateOrderCommand(
        dto.count(),
        dto.comment()
    );
  }
}