package com.winner.client.deliveryservice.delivery.application.dto.command;

import java.util.UUID;

public record CompanyDeliveryManagerAssignFailCommand(
    String failReason,
    UUID deliveryId
) {}
