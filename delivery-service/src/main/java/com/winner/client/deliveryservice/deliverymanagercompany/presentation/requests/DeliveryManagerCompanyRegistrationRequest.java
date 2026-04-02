package com.winner.client.deliveryservice.deliverymanagercompany.presentation.requests;


import com.winner.client.deliveryservice.deliverymanagercompany.application.command.DeliveryManagerCompanyRegistrationCommand;
import java.util.UUID;

public record DeliveryManagerCompanyRegistrationRequest (
	UUID userId,
	UUID hubId
) {

	public static DeliveryManagerCompanyRegistrationCommand toCommand(UUID userId, UUID hubId) {
		return new DeliveryManagerCompanyRegistrationCommand(userId, hubId);
	}

}
