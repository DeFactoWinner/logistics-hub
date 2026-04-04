package com.winner.client.deliveryservice.deliverymanagerhub.application.message;

import com.winner.client.deliveryservice.deliverymanagerhub.application.dto.result.DeliveryAssignResult;
import java.util.UUID;

public interface DeliveryMessagePort {
	void assignEventPublish(DeliveryAssignResult result);
}
