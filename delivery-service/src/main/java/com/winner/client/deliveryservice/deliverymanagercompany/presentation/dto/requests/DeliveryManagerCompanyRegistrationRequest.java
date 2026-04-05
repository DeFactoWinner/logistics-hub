package com.winner.client.deliveryservice.deliverymanagercompany.presentation.dto.requests;


import com.winner.client.deliveryservice.deliverymanagercompany.application.dto.command.DeliveryManagerCompanyRegistrationCommand;
import java.util.UUID;

public record DeliveryManagerCompanyRegistrationRequest (
	UUID userId,
	UUID hubId,
	String name
) {

	public DeliveryManagerCompanyRegistrationCommand toCommand() {
		return new DeliveryManagerCompanyRegistrationCommand(userId, hubId, name);
	}

}
