package com.winner.client.deliveryservice.deliverymanagercompany.application.dto.result;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = lombok.AccessLevel.PRIVATE)
public class CompanyDeliveryManagerAssignResult {

	private final boolean success;
	private final String errorMessage;
	private final String deliveryManagerName;
	private final UUID deliveryManagerId;
	private final UUID deliveryId;

	public static CompanyDeliveryManagerAssignResult success(UUID deliveryId, UUID deliveryManagerId, String deliveryManagerName) {
		return CompanyDeliveryManagerAssignResult.builder()
			.success(true)
			.deliveryManagerName(deliveryManagerName)
			.deliveryManagerId(deliveryManagerId)
			.deliveryId(deliveryId)
			.build();
	}

	public static CompanyDeliveryManagerAssignResult fail(String errorMessage, UUID deliveryId) {
		return CompanyDeliveryManagerAssignResult.builder()
				.success(false)
				.errorMessage(errorMessage)
				.deliveryId(deliveryId)
				.build();
	}

}
