package com.winner.client.deliveryservice.common.event.deliverymanager.hub;

public record AssignHubDeliveryManagerFailEvent(
	String message
) {

	public static AssignHubDeliveryManagerFailEvent from(String message) {
		return new AssignHubDeliveryManagerFailEvent(message);
	}

}
