package com.winner.client.deliveryservice.deliverymanagerhub.presentation.responses;

import com.winner.client.deliveryservice.deliverymanagerhub.application.result.DeliveryManagerHubInfoResult;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record DeliveryManagerHubInfo(
	UUID userId,
	UUID deliveryId,
	String status,
	Long assignmentOrder,
	LocalDateTime lastDeliveryCompletedTime
) {

	public static DeliveryManagerHubInfo from (DeliveryManagerHubInfoResult result) {
		return DeliveryManagerHubInfo.builder()
			.userId(result.userId())
			.deliveryId(result.deliveryId())
			.status(result.status())
			.assignmentOrder(result.assignmentOrder())
			.lastDeliveryCompletedTime(result.lastDeliveryCompletedTime())
			.build();
	}
}
