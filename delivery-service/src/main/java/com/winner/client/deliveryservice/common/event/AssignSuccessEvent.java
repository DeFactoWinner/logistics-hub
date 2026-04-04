package com.winner.client.deliveryservice.common.event;

import java.util.UUID;

public record AssignSuccessEvent (
	UUID deliveryId,
	UUID orderId
) {
	public static AssignSuccessEvent of(UUID deliveryId, UUID orderId) {
		return new AssignSuccessEvent(deliveryId, orderId);
	}
}
