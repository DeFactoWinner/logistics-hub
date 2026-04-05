package com.winner.client.deliveryservice.delivery.infrastructure.message;

import com.winner.client.deliveryservice.common.event.deliverymanager.hub.AssignFailEvent;
import com.winner.client.deliveryservice.common.event.deliverymanager.hub.AssignSuccessEvent;
import com.winner.client.deliveryservice.delivery.application.service.DeliveryMessageUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliverySpringEventListener {

	private final DeliveryMessageUsecase usecase;

	@EventListener
	public void successEventPublish(AssignSuccessEvent event) {
	}

	@EventListener
	public void failEventPublish(AssignFailEvent event) {
	}
}
