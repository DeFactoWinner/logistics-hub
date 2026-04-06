package com.winner.client.deliveryservice.delivery.infrastructure.client;

import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="order-service")
public interface OrderClient {
  @PostMapping("/internal/v1/orders/{orderId}}")
  void updateOrderInfo(@PathVariable UUID orderId, @RequestParam UUID deliveryManagerId);

  @PatchMapping("/internal/v1/orders/{orderId}/completion")
  void updateDeliveryCompleted(@PathVariable UUID orderId);
}
