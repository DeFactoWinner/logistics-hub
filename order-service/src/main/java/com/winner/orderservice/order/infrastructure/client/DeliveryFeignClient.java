package com.winner.orderservice.order.infrastructure.client;

import com.winner.client.global.response.ApiResponse;
import com.winner.orderservice.order.infrastructure.client.dto.request.CreateDeliveryRequest;
import com.winner.orderservice.order.infrastructure.client.dto.response.DeliveryResponse;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "delivery-service", url = "${feign.delivery-service.url:}")
public interface DeliveryFeignClient {

  @PostMapping("/internal/api/v1/deliveries")
  ApiResponse<DeliveryResponse> createDelivery(@RequestBody CreateDeliveryRequest request);

  @DeleteMapping("/api/v1/deliveries/{deliveryId}")
  ApiResponse<Void> cancelDelivery(@PathVariable UUID deliveryId);
}
