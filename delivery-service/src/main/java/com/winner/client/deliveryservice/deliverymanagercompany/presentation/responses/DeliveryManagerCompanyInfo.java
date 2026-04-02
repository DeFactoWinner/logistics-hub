package com.winner.client.deliveryservice.deliverymanagercompany.presentation.responses;

import com.winner.client.deliveryservice.deliverymanagercompany.application.result.DeliveryManagerCompanyInfoResult;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record DeliveryManagerCompanyInfo(
	UUID userId,
	UUID hubId,
	String status,
	Long assignmentOrder,
	LocalDateTime lastDeliveryCompletedTime
) {

	public static DeliveryManagerCompanyInfo from(DeliveryManagerCompanyInfoResult result) {
		return DeliveryManagerCompanyInfo.builder()
			.userId(result.userId())
			.hubId(result.hubId())
			.status(result.status())
			.assignmentOrder(result.assignmentOrder())
			.lastDeliveryCompletedTime(result.lastDeliveryCompletedTime())
			.build();
	}
}
