package com.winner.client.deliveryservice.deliverymanagerhub.application.dto.result;

import com.winner.client.deliveryservice.deliverymanagerhub.application.dto.commnad.AssignEventCommand;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class DeliveryAssignResult {

	private final boolean success;
	private final String errorMessage;
	private final UUID deliveryId;
	private final UUID orderId;

	public static DeliveryAssignResult success(AssignEventCommand command) {
		return DeliveryAssignResult.builder()
			.success(true)
			.deliveryId(command.deliveryId())
			.orderId(command.orderId())
			.build();
	}

	public static DeliveryAssignResult fail(String errorMessage) {
		return DeliveryAssignResult.builder()
			.success(false)
			.errorMessage(errorMessage)
			.build();
	}
}
