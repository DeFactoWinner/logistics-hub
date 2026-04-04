package com.winner.client.deliveryservice.deliverymanagercompany.application.dto.command;

import java.util.UUID;

public record DeliveryManagerCompanyRegistrationCommand(
	UUID userId,
	UUID hubId
) {

}
