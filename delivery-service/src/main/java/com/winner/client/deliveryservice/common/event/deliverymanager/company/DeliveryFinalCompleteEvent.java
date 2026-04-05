package com.winner.client.deliveryservice.common.event.deliverymanager.company;

import com.winner.client.deliveryservice.deliverymanagercompany.application.dto.result.DeliveryFinalCompleteResult;
import java.util.UUID;

public record DeliveryFinalCompleteEvent(
	UUID managerId,
	UUID deliveryId
) {
	public static DeliveryFinalCompleteEvent from(DeliveryFinalCompleteResult result) {
		return new DeliveryFinalCompleteEvent(result.managerId(), result.deliveryId());
	}
}
