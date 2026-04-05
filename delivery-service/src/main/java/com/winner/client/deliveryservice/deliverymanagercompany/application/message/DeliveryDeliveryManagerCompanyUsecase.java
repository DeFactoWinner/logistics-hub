package com.winner.client.deliveryservice.deliverymanagercompany.application.message;

import com.winner.client.deliveryservice.deliverymanagercompany.application.dto.command.DeliveryManagerAssignEventCommand;

public interface DeliveryDeliveryManagerCompanyUsecase {
	void assignHubDeliveryManager(DeliveryManagerAssignEventCommand command);
}
