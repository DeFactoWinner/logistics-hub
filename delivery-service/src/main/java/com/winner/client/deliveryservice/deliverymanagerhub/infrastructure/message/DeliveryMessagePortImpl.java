package com.winner.client.deliveryservice.deliverymanagerhub.infrastructure.message;

import com.winner.client.deliveryservice.common.event.AssignFailEvent;
import com.winner.client.deliveryservice.common.event.AssignSuccessEvent;
import com.winner.client.deliveryservice.common.event.DeliveryCompleteEvent;
import com.winner.client.deliveryservice.deliverymanagerhub.application.dto.result.DeliveryAssignResult;
import com.winner.client.deliveryservice.deliverymanagerhub.application.dto.result.DeliveryCompleteResult;
import com.winner.client.deliveryservice.deliverymanagerhub.application.message.DeliveryMessagePort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryMessagePortImpl implements DeliveryMessagePort {

	private final ApplicationEventPublisher publisher;

	@Override
	public void assignEventPublish(DeliveryAssignResult result) {
		if (result.isSuccess()) {
			publisher.publishEvent(
				AssignSuccessEvent.of(result.getDeliveryId(), result.getOrderId())
			);
		} else {
			publisher.publishEvent(AssignFailEvent.from(result.getErrorMessage()));
		}
	}

	@Override
	public void deliveryCompleteEventPublish(DeliveryCompleteResult result) {
		publisher.publishEvent(DeliveryCompleteEvent.from(result));
	}
}
