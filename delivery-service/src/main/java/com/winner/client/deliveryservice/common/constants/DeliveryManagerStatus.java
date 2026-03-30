package com.winner.client.deliveryservice.common.constants;

public enum DeliveryManagerStatus {
	AVAILABLE,
	IN_DELIVERY,
	OFF_DUTY;

	public boolean isAvailable() {
		return this == AVAILABLE;
	}
}

