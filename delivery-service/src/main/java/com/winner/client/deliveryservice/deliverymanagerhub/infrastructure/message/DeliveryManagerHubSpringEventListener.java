package com.winner.client.deliveryservice.deliverymanagerhub.infrastructure.message;

import com.winner.client.deliveryservice.common.event.AssignDeliveryManagerHubEvent;
import com.winner.client.deliveryservice.deliverymanagerhub.application.dto.commnad.DeliveryManagerAssignEventCommand;
import com.winner.client.deliveryservice.deliverymanagerhub.application.message.DeliveryDeliveryManagerHubUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryManagerHubSpringEventListener {

	private final DeliveryDeliveryManagerHubUsecase usecase;

	@EventListener
	public void assignDeliveryManagerHub(AssignDeliveryManagerHubEvent event) {
		usecase.assignHubDeliveryManager(DeliveryManagerAssignEventCommand.of(event.deliveryId()));
	}
}
