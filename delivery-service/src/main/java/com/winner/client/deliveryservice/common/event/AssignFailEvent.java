package com.winner.client.deliveryservice.common.event;

public record AssignFailEvent(
	String message
) {

	public static AssignFailEvent from(String message) {
		return new AssignFailEvent(message);
	}

}
