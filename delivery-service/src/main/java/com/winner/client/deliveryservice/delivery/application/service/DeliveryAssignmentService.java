package com.winner.client.deliveryservice.delivery.application.service;

import com.winner.client.deliveryservice.delivery.domain.entity.Delivery;
import com.winner.client.deliveryservice.delivery.domain.entity.DeliveryRoute;
import java.util.List;
import java.util.UUID;

public interface DeliveryAssignmentService {
  void assignCompanyDeliveryManager(Delivery delivery);
  void assignHubDeliveryManager(Delivery delivery, List<DeliveryRoute> routes);
  void retryHubDeliveryManager(UUID deliveryId);
}
