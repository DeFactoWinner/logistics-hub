package com.winner.client.deliveryservice.deliverymanagerhub.application.dto.commnad;

import java.util.UUID;

public record DeliveryManagerAssignEventCommand(
	UUID deliveryId
) {
	public static DeliveryManagerAssignEventCommand of(UUID deliveryId) {
		return new DeliveryManagerAssignEventCommand(deliveryId);
	}
}
