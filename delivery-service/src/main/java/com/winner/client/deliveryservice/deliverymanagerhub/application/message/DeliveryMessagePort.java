package com.winner.client.deliveryservice.deliverymanagerhub.application.message;

import java.util.UUID;

public interface DeliveryMessagePort {
	void failEventPublish(String message);
	void successEventPublish(UUID deliveryId, UUID orderId);
}
