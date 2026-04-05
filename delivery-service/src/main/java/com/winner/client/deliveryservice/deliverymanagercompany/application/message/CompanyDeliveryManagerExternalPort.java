package com.winner.client.deliveryservice.deliverymanagercompany.application.message;

import com.winner.client.deliveryservice.deliverymanagercompany.application.dto.re.CompanyDeliveryManagerAssignResult;

public interface CompanyDeliveryManagerExternalPort {
	void assignEventPublish(CompanyDeliveryManagerAssignResult result);
}
