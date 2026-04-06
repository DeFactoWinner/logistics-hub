package com.winner.client.deliveryservice.deliverymanagercompany.infrastructure.message;

import com.winner.client.deliveryservice.common.event.AssignDeliveryManagerCompanyEvent;
import com.winner.client.deliveryservice.deliverymanagercompany.application.dto.command.DeliveryManagerAssignEventCommand;
import com.winner.client.deliveryservice.deliverymanagercompany.application.message.DeliveryDeliveryManagerCompanyUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryManagerCompanySpringEventListener {

	private final DeliveryDeliveryManagerCompanyUsecase usecase;

	@EventListener
	public void assignDeliveryManagerCompany(AssignDeliveryManagerCompanyEvent event) {
		usecase.assignHubDeliveryManager(
			DeliveryManagerAssignEventCommand.of(event.deliveryId(), event.hubId()));
	}
}
