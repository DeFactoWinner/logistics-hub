package com.winner.client.deliveryservice.deliverymanagerhub.application.dto.result;

import java.util.UUID;

public record DeliveryCompleteResult(
	UUID managerId,
	UUID deliveryId
) {
	public static DeliveryCompleteResult of(UUID managerID, UUID deliveryId) {
		return new DeliveryCompleteResult(managerID, deliveryId);
	}
}
