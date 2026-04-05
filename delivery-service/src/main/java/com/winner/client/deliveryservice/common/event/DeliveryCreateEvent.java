package com.winner.client.deliveryservice.common.event;

import com.winner.client.deliveryservice.deliverymanagerhub.application.dto.commnad.DeliveryManagerAssignEventCommand;
import java.util.UUID;

public record DeliveryCreateEvent(
	UUID deliveryId
) {

	public DeliveryManagerAssignEventCommand toCommand() {
		return new DeliveryManagerAssignEventCommand(this.deliveryId);
	}
}
