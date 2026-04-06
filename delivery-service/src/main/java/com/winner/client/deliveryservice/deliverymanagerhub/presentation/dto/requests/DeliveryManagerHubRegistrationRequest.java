package com.winner.client.deliveryservice.deliverymanagerhub.presentation.dto.requests;

import com.winner.client.deliveryservice.deliverymanagerhub.application.dto.commnad.DeliveryManagerHubRegistrationCommand;
import java.util.UUID;

public record DeliveryManagerHubRegistrationRequest(
	UUID userId,
	String name
) {

	public DeliveryManagerHubRegistrationCommand toCommand() {
		return new DeliveryManagerHubRegistrationCommand(userId, name);
	}
}
