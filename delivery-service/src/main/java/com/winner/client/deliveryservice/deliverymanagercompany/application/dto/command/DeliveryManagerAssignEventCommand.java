package com.winner.client.deliveryservice.deliverymanagercompany.application.dto.command;

import java.util.UUID;

public record DeliveryManagerAssignEventCommand(
	UUID deliveryId,
	UUID hubId
) {
	public static DeliveryManagerAssignEventCommand of(UUID deliveryId, UUID hubId) {
		return new DeliveryManagerAssignEventCommand(deliveryId, hubId);
	}
}
