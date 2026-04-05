package com.winner.client.deliveryservice.delivery.infrastructure.client;

import com.winner.client.deliveryservice.delivery.application.port.OrderPort;
import com.winner.client.deliveryservice.delivery.infrastructure.client.dto.UpdateOrderInfoRequest;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderClientAdapter implements OrderPort {

  private final OrderClient orderClient;

  @Override
  public void updateOrderDeliveryInfo(UUID deliveryId, UUID deliveryManagerId, String deliveryStatus) {
    orderClient.updateOrderInfo(
        new UpdateOrderInfoRequest(deliveryId, deliveryManagerId, deliveryStatus)
    );
  }

  @Override
  public void updateOrderDeliveryCompleted(UUID deliveryId) {
    orderClient.updateDeliveryCompleted(deliveryId);
  }
}