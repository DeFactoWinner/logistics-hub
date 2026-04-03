package com.winner.client.deliveryservice.delivery.application.service;

import com.winner.client.deliveryservice.delivery.application.dto.command.CreateDeliveryCommand;
import com.winner.client.deliveryservice.delivery.presentation.dto.request.CreateDeliveryRequest;
import com.winner.client.deliveryservice.delivery.presentation.dto.response.DeliveryCommandResponse;
import java.util.UUID;

public interface DeliveryCommandService {
  UUID createDelivery(CreateDeliveryCommand command);
  DeliveryCommandResponse startHubWaiting(
      UUID deliveryId, String userRole, UUID referenceId);
  DeliveryCommandResponse startHubMoving(
      UUID deliveryId, String userRole, UUID referenceId);
  DeliveryCommandResponse startVendorMoving(
      UUID deliveryId, UUID userId, String userRole, UUID referenceId);
  DeliveryCommandResponse arriveDestination(
      UUID deliveryId, String userRole, UUID referenceId);
  DeliveryCommandResponse completeDelivery(
      UUID deliveryId, UUID userId, String userRole, UUID referenceId);
  DeliveryCommandResponse cancelDelivery(
      UUID deliveryId, String userRole);
}
