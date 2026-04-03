package com.winner.client.deliveryservice.deliverymanagerhub.presentation.requests;

import com.winner.client.deliveryservice.deliverymanagerhub.application.dto.commnad.DeliveryManagerHubRegistrationCommand;
import java.util.UUID;

public record DeliveryManagerHubRegistrationRequest(
	UUID userId
) {
	public static DeliveryManagerHubRegistrationCommand toCommand(UUID userId) {
		return new DeliveryManagerHubRegistrationCommand(userId);
	}
}
