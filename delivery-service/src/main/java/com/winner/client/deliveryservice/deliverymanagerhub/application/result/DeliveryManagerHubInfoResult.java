package com.winner.client.deliveryservice.deliverymanagerhub.application.result;

import com.winner.client.deliveryservice.deliverymanagerhub.domain.entity.DeliveryManagerHub;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record DeliveryManagerHubInfoResult(
	UUID userId,
	String status,
	Long assignmentOrder,
	LocalDateTime lastDeliveryCompletedTime
) {

	public static DeliveryManagerHubInfoResult from(DeliveryManagerHub entity) {
		return DeliveryManagerHubInfoResult.builder()
			.userId(entity.getUserId())
			.status(entity.getDeliveryManagerStatus().name())
			.assignmentOrder(entity.getAssignmentOrder())
			.lastDeliveryCompletedTime(entity.getLastDeliveryCompletedTime())
			.build();
	}
}
