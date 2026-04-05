package com.winner.client.deliveryservice.deliverymanagercompany.presentation.dto.responses;

import com.winner.client.deliveryservice.deliverymanagercompany.application.dto.result.DeliveryManagerCompanyInfoResult;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record DeliveryManagerCompanyInfo(
	UUID userId,
	UUID hubId,
	UUID deliveryId,
	String name,
	String status,
	Long assignmentOrder,
	LocalDateTime lastDeliveryCompletedTime
) {

	public static DeliveryManagerCompanyInfo from(DeliveryManagerCompanyInfoResult result) {
		return DeliveryManagerCompanyInfo.builder()
			.userId(result.userId())
			.hubId(result.hubId())
			.deliveryId(result.deliveryId())
			.name(result.name())
			.status(result.status())
			.assignmentOrder(result.assignmentOrder())
			.lastDeliveryCompletedTime(result.lastDeliveryCompletedTime())
			.build();
	}
}
