package com.winner.client.deliveryservice.delivery.application.dto.command;

import com.winner.client.deliveryservice.common.event.deliverymanager.hub.AssignHubDeliveryManagerSuccessEvent;
import java.util.UUID;

public record DeliveryRouteAssignCompleteCommand(
    UUID deliveryId,
    UUID deliveryManagerId,
    String deliveryManagerName
) {
  public static DeliveryRouteAssignCompleteCommand from(AssignHubDeliveryManagerSuccessEvent event) {
    return new DeliveryRouteAssignCompleteCommand(
        event.deliveryId(),
        event.deliveryManagerId(),
        event.name()
    );
  }
}
