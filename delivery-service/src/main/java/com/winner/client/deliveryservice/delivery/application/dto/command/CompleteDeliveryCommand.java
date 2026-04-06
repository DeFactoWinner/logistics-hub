package com.winner.client.deliveryservice.delivery.application.dto.command;

import com.winner.client.deliveryservice.common.event.deliverymanager.company.DeliveryFinalCompleteEvent;
import java.util.UUID;

public record CompleteDeliveryCommand(
    UUID deliveryId, UUID deliveryManagerId
) {
  public static CompleteDeliveryCommand from(DeliveryFinalCompleteEvent event) {
    return new CompleteDeliveryCommand(
        event.deliveryId(),
        event.managerId()
    );
  }
}
