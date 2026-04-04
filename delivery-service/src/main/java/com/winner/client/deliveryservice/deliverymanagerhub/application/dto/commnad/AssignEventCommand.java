package com.winner.client.deliveryservice.deliverymanagerhub.application.dto.commnad;

import java.util.UUID;

public record AssignEventCommand (
	UUID deliveryId,
	UUID orderId
) {

}
