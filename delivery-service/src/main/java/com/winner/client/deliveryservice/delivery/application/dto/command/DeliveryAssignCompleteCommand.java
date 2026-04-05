package com.winner.client.deliveryservice.delivery.application.dto.command;

import java.util.UUID;

public record DeliveryAssignCompleteCommand(
    UUID deliveryId,
    UUID deliveryManagerId,
    String deliveryManagerName
) {
}
