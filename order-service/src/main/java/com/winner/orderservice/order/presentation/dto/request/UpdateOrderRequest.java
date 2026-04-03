package com.winner.orderservice.order.presentation.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateOrderRequest(
    @NotNull @Min(1) Long count,
    String comment
) {}

