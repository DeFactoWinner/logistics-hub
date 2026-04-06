package com.winner.client.deliveryservice.delivery.application.port;

import java.util.UUID;

public interface OrderPort {
  void updateOrderDeliveryInfo(UUID deliveryId, UUID deliveryManagerId, String deliveryStatus);
  void updateOrderDeliveryCompleted(UUID deliveryId);
}