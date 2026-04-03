package com.winner.client.deliveryservice.common.event;

import com.winner.client.deliveryservice.deliverymanagerhub.application.dto.commnad.AssignEventCommand;
import java.util.UUID;

public record DeliveryCreateEvent(
	UUID deliveryId,
	UUID orderId
) {

	public AssignEventCommand toCommand() {
		return new AssignEventCommand(this.deliveryId, this.orderId);
	}
}
