package com.winner.client.deliveryservice.deliverymanagercompany.application.message;

import com.winner.client.deliveryservice.deliverymanagercompany.application.dto.result.CompanyDeliveryManagerAssignResult;
import com.winner.client.deliveryservice.deliverymanagercompany.application.dto.result.DeliveryFinalCompleteResult;

public interface CompanyDeliveryManagerExternalPort {
	void assignEventPublish(CompanyDeliveryManagerAssignResult result);
	void deliveryFinalCompleteEventPublish(DeliveryFinalCompleteResult result);
}
