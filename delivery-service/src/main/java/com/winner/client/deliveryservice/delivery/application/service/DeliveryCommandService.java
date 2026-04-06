package com.winner.client.deliveryservice.delivery.application.service;

import com.winner.client.deliveryservice.delivery.application.dto.command.CreateDeliveryCommand;
import com.winner.client.deliveryservice.delivery.application.dto.result.CreateDeliveryResult;
import com.winner.client.deliveryservice.delivery.presentation.dto.response.DeliveryCommandResponse;
import java.util.UUID;

public interface DeliveryCommandService {
  CreateDeliveryResult createDelivery(CreateDeliveryCommand command);
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
  void deleteDelivery(UUID deliveryId, UUID userId, String userRole);
}
