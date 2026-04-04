package com.winner.client.deliveryservice.deliverymanagerhub.application.message;

import com.winner.client.deliveryservice.deliverymanagerhub.application.dto.commnad.AssignEventCommand;


public interface DeliveryDeliveryManagerHubUsecase {
	void assignHubDeliveryManager(AssignEventCommand command);
}
