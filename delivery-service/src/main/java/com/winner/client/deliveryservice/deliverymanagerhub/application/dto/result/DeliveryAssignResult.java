package com.winner.client.deliveryservice.deliverymanagerhub.application.dto.result;

import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class DeliveryAssignResult {

	private final boolean success;
	private final String errorMessage;
	private final String deliveryManagerName;
	private final UUID deliveryManagerId;
	private final UUID deliveryId;

	public static DeliveryAssignResult success(UUID deliveryId, UUID deliveryManagerId, String deliveryManagerName) {
		return DeliveryAssignResult.builder()
			.success(true)
			.deliveryManagerName(deliveryManagerName)
			.deliveryManagerId(deliveryManagerId)
			.deliveryId(deliveryId)
			.build();
	}

	public static DeliveryAssignResult fail(String errorMessage) {
		return DeliveryAssignResult.builder()
			.success(false)
			.errorMessage(errorMessage)
			.build();
	}
}
