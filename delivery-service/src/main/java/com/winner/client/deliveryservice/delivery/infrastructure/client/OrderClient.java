package com.winner.client.deliveryservice.delivery.infrastructure.client;

import com.winner.client.deliveryservice.delivery.infrastructure.client.dto.UpdateOrderInfoRequest;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="order-service")
public interface OrderClient {
  @PatchMapping("/internal/v1/orders}")
  void updateOrderInfo(@RequestBody UpdateOrderInfoRequest request);

  @PatchMapping("/internal/v1/orders")
  void updateDeliveryCompleted(@RequestParam UUID deliveryId);
}
