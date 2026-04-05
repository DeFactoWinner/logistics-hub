package com.winner.client.deliveryservice.common.event;

import com.winner.client.deliveryservice.deliverymanagerhub.application.dto.result.DeliveryCompleteResult;
import java.util.UUID;

public record DeliveryCompleteEvent(
	UUID managerId,
	UUID deliveryId
) {

	public static DeliveryCompleteEvent from(DeliveryCompleteResult result) {
		return new DeliveryCompleteEvent(result.managerId(), result.deliveryId());
	}
}
