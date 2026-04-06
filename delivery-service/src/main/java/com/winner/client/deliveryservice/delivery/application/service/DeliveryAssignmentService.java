package com.winner.client.deliveryservice.delivery.application.service;

import com.winner.client.deliveryservice.delivery.domain.entity.Delivery;
import java.util.UUID;

public interface DeliveryAssignmentService {
  void assignCompanyDeliveryManager(Delivery delivery);
  void assignHubDeliveryManager(Delivery delivery);
  void retryHubDeliveryManager(UUID deliveryId);
}
