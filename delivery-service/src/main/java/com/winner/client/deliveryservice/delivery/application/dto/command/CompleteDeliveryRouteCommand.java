package com.winner.client.deliveryservice.delivery.application.dto.command;

import com.winner.client.deliveryservice.common.event.deliverymanager.hub.DeliveryCompleteEvent;
import java.util.UUID;

public record CompleteDeliveryRouteCommand(
    UUID deliveryId, UUID deliveryManagerId
) {
  public static CompleteDeliveryRouteCommand from(DeliveryCompleteEvent event) {
    return new CompleteDeliveryRouteCommand(
        event.deliveryId(),
        event.managerId()
    );
  }
}
