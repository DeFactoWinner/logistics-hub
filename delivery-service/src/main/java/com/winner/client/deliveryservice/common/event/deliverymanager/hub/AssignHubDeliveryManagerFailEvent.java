package com.winner.client.deliveryservice.common.event.deliverymanager.hub;

import java.util.UUID;

public record AssignHubDeliveryManagerFailEvent(
	String message,
	UUID deliveryId
) {
	public static AssignHubDeliveryManagerFailEvent of(String message, UUID deliveryId) {
		return new AssignHubDeliveryManagerFailEvent(message, deliveryId);
	}
}
