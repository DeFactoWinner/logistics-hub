package com.winner.client.deliveryservice.common.event.deliverymanager.hub;

public record AssignFailEvent(
	String message
) {

	public static AssignFailEvent from(String message) {
		return new AssignFailEvent(message);
	}

}
