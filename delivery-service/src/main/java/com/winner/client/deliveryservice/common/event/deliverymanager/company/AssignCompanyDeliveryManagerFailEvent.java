package com.winner.client.deliveryservice.common.event.deliverymanager.company;

import com.winner.client.deliveryservice.deliverymanagercompany.application.dto.re.CompanyDeliveryManagerAssignResult;
import java.util.UUID;

public record AssignCompanyDeliveryManagerFailEvent(
	String message,
	UUID deliveryId
) {
	public static AssignCompanyDeliveryManagerFailEvent from(CompanyDeliveryManagerAssignResult result) {
		return new AssignCompanyDeliveryManagerFailEvent(
			result.getErrorMessage(),
			result.getDeliveryId()
		);
	}
}
