package com.winner.client.deliveryservice.deliverymanagerhub.application.message;

import com.winner.client.deliveryservice.deliverymanagerhub.application.dto.commnad.DeliveryManagerAssignEventCommand;


public interface DeliveryDeliveryManagerHubUsecase {
	void assignHubDeliveryManager(DeliveryManagerAssignEventCommand command);
}
