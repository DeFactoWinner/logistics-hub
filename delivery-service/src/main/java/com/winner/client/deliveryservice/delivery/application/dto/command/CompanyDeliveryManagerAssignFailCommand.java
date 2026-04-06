package com.winner.client.deliveryservice.delivery.application.dto.command;

import com.winner.client.deliveryservice.common.event.deliverymanager.company.AssignCompanyDeliveryManagerFailEvent;
import java.util.UUID;

public record CompanyDeliveryManagerAssignFailCommand(
    String failReason,
    UUID deliveryId
) {
  public static CompanyDeliveryManagerAssignFailCommand from(
      AssignCompanyDeliveryManagerFailEvent event) {
    return new CompanyDeliveryManagerAssignFailCommand(
        event.message(), event.deliveryId());
  }
}
