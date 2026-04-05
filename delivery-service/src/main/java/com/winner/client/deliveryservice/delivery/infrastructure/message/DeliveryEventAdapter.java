package com.winner.client.deliveryservice.delivery.infrastructure.message;

import com.winner.client.deliveryservice.common.event.AssignDeliveryManagerCompanyEvent;
import com.winner.client.deliveryservice.common.event.AssignDeliveryManagerHubEvent;
import com.winner.client.deliveryservice.delivery.application.port.DeliveryEventPort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryEventAdapter implements DeliveryEventPort {

  private final ApplicationEventPublisher eventPublisher;

  @Override
  public void publishAssignHubEvent(AssignDeliveryManagerHubEvent event) {
    eventPublisher.publishEvent(event);
  }

  @Override
  public void publishAssignCompanyEvent(AssignDeliveryManagerCompanyEvent event) {
    eventPublisher.publishEvent(event);
  }
}
