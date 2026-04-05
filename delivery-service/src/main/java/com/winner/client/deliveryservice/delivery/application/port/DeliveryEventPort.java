package com.winner.client.deliveryservice.delivery.application.port;

import com.winner.client.deliveryservice.common.event.AssignDeliveryManagerCompanyEvent;
import com.winner.client.deliveryservice.common.event.AssignDeliveryManagerHubEvent;

public interface DeliveryEventPort {
  void publishAssignHubEvent(AssignDeliveryManagerHubEvent event);
  void publishAssignCompanyEvent(AssignDeliveryManagerCompanyEvent event);
}
