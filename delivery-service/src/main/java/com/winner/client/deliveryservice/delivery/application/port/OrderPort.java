package com.winner.client.deliveryservice.delivery.application.port;

import java.util.UUID;

public interface OrderPort {
  void updateOrderDeliveryInfo(UUID orderId, UUID deliveryManagerId);
  void updateOrderDeliveryCompleted(UUID orderId);
}