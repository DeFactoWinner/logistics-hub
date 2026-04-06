package com.winner.client.deliveryservice.delivery.application.dto.command;

import com.winner.client.deliveryservice.common.event.deliverymanager.company.AssignCompanyDeliveryManagerSuccessEvent;
import java.util.UUID;

public record DeliveryAssignCompleteCommand(
    UUID deliveryId,
    UUID deliveryManagerId,
    String deliveryManagerName
) {
  public static DeliveryAssignCompleteCommand from(AssignCompanyDeliveryManagerSuccessEvent event) {
    return new DeliveryAssignCompleteCommand(
        event.deliveryId(),
        event.deliveryManagerId(),
        event.name()
    );
  }
}
