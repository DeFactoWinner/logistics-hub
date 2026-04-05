package com.winner.client.deliveryservice.delivery.infrastructure.message;

import com.winner.client.deliveryservice.common.event.AssignFailEvent;
import com.winner.client.deliveryservice.common.event.AssignSuccessEvent;
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
