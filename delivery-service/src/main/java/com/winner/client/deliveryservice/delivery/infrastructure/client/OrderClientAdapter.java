package com.winner.client.deliveryservice.delivery.infrastructure.client;

import com.winner.client.deliveryservice.delivery.application.port.OrderPort;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderClientAdapter implements OrderPort {

  private final OrderClient orderClient;

  @Override
  public void updateOrderDeliveryInfo(UUID orderId, UUID deliveryManagerId) {
    orderClient.updateOrderInfo(orderId, deliveryManagerId);
  }

  @Override
  public void updateOrderDeliveryCompleted(UUID orderId) {
    orderClient.updateDeliveryCompleted(orderId);
  }
}