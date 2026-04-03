package com.winner.orderservice.order.presentation.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public record CreateOrderRequest(
    @NotNull UUID supplierId,
    @NotNull UUID receiverId,
    @NotNull UUID productId,
    @NotNull @Min(1) Long count,
    String comment,
    @NotBlank String deliveryAddress,
    @NotBlank String deliveryAddressDetail,
    @NotNull LocalDateTime orderedAt
) {}

