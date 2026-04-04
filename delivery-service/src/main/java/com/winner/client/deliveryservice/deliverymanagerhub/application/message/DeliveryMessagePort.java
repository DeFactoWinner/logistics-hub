package com.winner.client.deliveryservice.deliverymanagerhub.application.message;

import com.winner.client.deliveryservice.deliverymanagerhub.application.dto.result.DeliveryAssignResult;

public interface DeliveryMessagePort {
	void assignEventPublish(DeliveryAssignResult result);
}
