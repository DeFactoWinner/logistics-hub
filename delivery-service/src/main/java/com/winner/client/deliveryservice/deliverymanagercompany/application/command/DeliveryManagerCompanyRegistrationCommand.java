package com.winner.client.deliveryservice.deliverymanagercompany.application.command;

import java.util.UUID;

public record DeliveryManagerCompanyRegistrationCommand(
	UUID userId,
	UUID hubId
) {

}
