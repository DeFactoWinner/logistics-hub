package com.winner.client.deliveryservice.deliverymanagerhub.application.dto.result;

import com.winner.client.deliveryservice.deliverymanagerhub.domain.entity.DeliveryManagerHub;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record DeliveryManagerHubInfoResult(
	UUID userId,
	UUID deliveryId,
	String name,
	String status,
	Long assignmentOrder,
	LocalDateTime lastDeliveryCompletedTime
) {

	public static DeliveryManagerHubInfoResult from(DeliveryManagerHub entity) {
		return DeliveryManagerHubInfoResult.builder()
			.userId(entity.getUserId())
			.deliveryId(entity.getDeliveryId())
			.name(entity.getName())
			.status(entity.getDeliveryManagerStatus().name())
			.assignmentOrder(entity.getAssignmentOrder())
			.lastDeliveryCompletedTime(entity.getLastDeliveryCompletedTime())
			.build();
	}
}
