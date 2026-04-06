package com.winner.client.deliveryservice.deliverymanagerhub.application.message;

import com.winner.client.deliveryservice.deliverymanagerhub.application.dto.result.DeliveryAssignResult;
import com.winner.client.deliveryservice.deliverymanagerhub.application.dto.result.DeliveryCompleteResult;

public interface DeliveryMessagePort {
	void assignEventPublish(DeliveryAssignResult result);
	void deliveryCompleteEventPublish(DeliveryCompleteResult result);
}
