package com.winner.client.deliveryservice.common.event;

import java.util.UUID;

public record DeliveryCreateEvent(
	UUID deliveryId,
	UUID orderId
) {

}
