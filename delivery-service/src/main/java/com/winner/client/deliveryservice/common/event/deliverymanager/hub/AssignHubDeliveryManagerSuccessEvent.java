package com.winner.client.deliveryservice.common.event.deliverymanager.hub;

import java.util.UUID;

public record AssignHubDeliveryManagerSuccessEvent(
	UUID deliveryId,
	UUID deliveryManagerId,
	String name
) {
	public static AssignHubDeliveryManagerSuccessEvent of(UUID deliveryId, UUID deliveryManagerId, String name) {
		return new AssignHubDeliveryManagerSuccessEvent(deliveryId, deliveryManagerId, name);
	}
}
