package com.winner.client.deliveryservice.deliverymanagercompany.application.result;

import com.winner.client.deliveryservice.deliverymanagercompany.domain.entity.DeliveryManagerCompany;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record DeliveryManagerCompanyInfoResult(
	UUID userId,
	UUID hubId,
	UUID deliveryId,
	String status,
	Long assignmentOrder,
	LocalDateTime lastDeliveryCompletedTime
) {

	public static DeliveryManagerCompanyInfoResult from(
		DeliveryManagerCompany entity) {

		return DeliveryManagerCompanyInfoResult.builder()
			.userId(entity.getUserId())
			.hubId(entity.getHubId())
			.deliveryId(entity.getDeliveryId())
			.status(entity.getDeliveryManagerStatus().name())
			.assignmentOrder(entity.getAssignmentOrder())
			.lastDeliveryCompletedTime(entity.getLastDeliveryCompletedTime())
			.build();
	}
}
