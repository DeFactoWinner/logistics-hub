package com.winner.client.deliveryservice.deliverymanagercompany.infrastructure.message;

import com.winner.client.deliveryservice.common.event.deliverymanager.company.AssignCompanyDeliveryManagerFailEvent;
import com.winner.client.deliveryservice.common.event.deliverymanager.company.AssignCompanyDeliveryManagerSuccessEvent;
import com.winner.client.deliveryservice.common.event.deliverymanager.company.DeliveryFinalCompleteEvent;
import com.winner.client.deliveryservice.deliverymanagercompany.application.dto.result.CompanyDeliveryManagerAssignResult;
import com.winner.client.deliveryservice.deliverymanagercompany.application.dto.result.DeliveryFinalCompleteResult;
import com.winner.client.deliveryservice.deliverymanagercompany.application.message.CompanyDeliveryManagerExternalPort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompanyDeliveryManagerExternalPortImpl implements CompanyDeliveryManagerExternalPort {

	private final ApplicationEventPublisher publisher;

	@Override
	public void assignEventPublish(CompanyDeliveryManagerAssignResult result) {
		if (result.isSuccess()) {
			publisher.publishEvent(
				AssignCompanyDeliveryManagerSuccessEvent.from(result));
		} else {
			publisher.publishEvent(
				AssignCompanyDeliveryManagerFailEvent.from(result));
		}
	}

	@Override
	public void deliveryFinalCompleteEventPublish(DeliveryFinalCompleteResult result) {
		publisher.publishEvent(DeliveryFinalCompleteEvent.from(result));
	}
}
