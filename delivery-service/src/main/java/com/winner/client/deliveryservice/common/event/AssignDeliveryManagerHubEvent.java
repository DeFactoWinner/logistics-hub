package com.winner.client.deliveryservice.common.event;

import java.util.UUID;

public record AssignDeliveryManagerHubEvent(
    UUID deliveryId
) { }