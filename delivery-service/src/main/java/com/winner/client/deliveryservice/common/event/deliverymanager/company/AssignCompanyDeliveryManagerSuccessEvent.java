package com.winner.client.deliveryservice.common.event.deliverymanager.company;

import com.winner.client.deliveryservice.deliverymanagercompany.application.dto.result.CompanyDeliveryManagerAssignResult;
import java.util.UUID;

public record AssignCompanyDeliveryManagerSuccessEvent(
	UUID deliveryId,
	UUID deliveryManagerId,
	String name
) {
	public static AssignCompanyDeliveryManagerSuccessEvent from(CompanyDeliveryManagerAssignResult result) {
		return new AssignCompanyDeliveryManagerSuccessEvent(
			result.getDeliveryId(),
			result.getDeliveryManagerId(),
			result.getDeliveryManagerName()
		);
	}
}
