package com.winner.client.deliveryservice.deliverymanagercompany.application.result;

import com.winner.client.deliveryservice.deliverymanagercompany.domain.entity.DeliveryManagerCompany;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record DeliveryManagerCompanyRegistrationResult (
	UUID userId,
	UUID hubId,
	String status,
	Long assignmentOrder,
	LocalDateTime lastDeliveryCompletedTime
) {

	public static DeliveryManagerCompanyRegistrationResult from(
		DeliveryManagerCompany entity) {

		return DeliveryManagerCompanyRegistrationResult.builder()
			.userId(entity.getUserId())
			.hubId(entity.getHubId())
			.status(entity.getDeliveryManagerStatus().name())
			.assignmentOrder(entity.getAssignmentOrder())
			.lastDeliveryCompletedTime(entity.getLastDeliveryCompletedTime())
			.build();
	}
}
