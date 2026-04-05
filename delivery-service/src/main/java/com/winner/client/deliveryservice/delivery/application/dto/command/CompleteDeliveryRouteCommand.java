package com.winner.client.deliveryservice.delivery.application.dto.command;

import java.util.UUID;

public record CompleteDeliveryRouteCommand(
    UUID deliveryId, UUID deliveryManagerId
) {
}
