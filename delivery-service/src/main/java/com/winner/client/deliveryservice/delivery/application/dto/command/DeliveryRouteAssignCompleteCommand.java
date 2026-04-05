package com.winner.client.deliveryservice.delivery.application.dto.command;

import java.util.UUID;

public record DeliveryRouteAssignCompleteCommand(
    UUID deliveryRouteId,
    UUID orderId,
    UUID deliveryManagerId,
    String deliveryManagerName
) {}
