package com.winner.client.deliveryservice.common.event;

import java.util.UUID;

public record AssignSuccessEvent (
	UUID deliveryId,
	UUID deliveryManagerId
) {
	public static AssignSuccessEvent of(UUID deliveryId, UUID deliveryManagerId) {
		return new AssignSuccessEvent(deliveryId, deliveryManagerId);
	}
}
