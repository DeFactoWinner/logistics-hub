package com.winner.client.deliveryservice.delivery.presentation.dto.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record UpdateDeliveryRequest (
    @NotNull
    @Digits(integer = 10, fraction = 2, message = "거리 형식은 최대 10자리 정수, 2자리 소수까지 가능합니다.")
    @Positive(message = "거리는 0보다 커야 합니다.")
    BigDecimal actualDistance,

    @NotNull
    @Positive(message = "소요 시간은 0보다 커야 합니다.")
    double actualArrivalTime
){}
