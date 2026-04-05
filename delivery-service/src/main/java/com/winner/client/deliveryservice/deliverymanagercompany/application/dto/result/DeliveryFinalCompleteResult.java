package com.winner.client.deliveryservice.deliverymanagercompany.application.dto.result;

import java.util.UUID;

public record DeliveryFinalCompleteResult(
	UUID managerId,
	UUID deliveryId
) {

	public static DeliveryFinalCompleteResult of(UUID userId, UUID deliveryId) {
		return new DeliveryFinalCompleteResult(userId, deliveryId);
	}
}
