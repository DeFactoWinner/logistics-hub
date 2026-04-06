package com.winner.client.deliveryservice.delivery.application.dto.command;

import com.winner.client.deliveryservice.common.event.deliverymanager.hub.AssignHubDeliveryManagerFailEvent;
import java.util.UUID;

public record HubDeliveryManagerAssignFailCommand(
    String failReason,
    UUID deliveryId
) {
  public static HubDeliveryManagerAssignFailCommand from(AssignHubDeliveryManagerFailEvent event) {
    return new HubDeliveryManagerAssignFailCommand(
        event.message(),
        event.deliveryId()
    );
  }
}