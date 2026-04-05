package com.winner.client.deliveryservice.deliverymanagerhub.infrastructure.message;

import com.winner.client.deliveryservice.common.event.deliverymanager.hub.AssignHubDeliveryManagerFailEvent;
import com.winner.client.deliveryservice.common.event.deliverymanager.hub.AssignHubDeliveryManagerSuccessEvent;
import com.winner.client.deliveryservice.common.event.deliverymanager.hub.DeliveryCompleteEvent;
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
				AssignHubDeliveryManagerSuccessEvent
					.of(result.getDeliveryId(), result.getDeliveryManagerId(), result.getDeliveryManagerName())
			);
		} else {
			publisher.publishEvent(AssignHubDeliveryManagerFailEvent
				.of(result.getErrorMessage(), result.getDeliveryManagerName()));
		}
	}

	@Override
	public void deliveryCompleteEventPublish(DeliveryCompleteResult result) {
		publisher.publishEvent(DeliveryCompleteEvent.from(result));
	}
}
