package com.winner.client.deliveryservice.common.event.deliverymanager.hub;

public record AssignHubDeliveryManagerFailEvent(
	String message,
	String deliveryId
) {
	public static AssignHubDeliveryManagerFailEvent of(String message, String deliveryId) {
		return new AssignHubDeliveryManagerFailEvent(message, deliveryId);
	}
}
