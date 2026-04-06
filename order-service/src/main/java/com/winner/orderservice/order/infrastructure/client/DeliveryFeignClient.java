package com.winner.orderservice.order.infrastructure.client;

import com.winner.client.global.response.ApiResponse;
import com.winner.orderservice.order.infrastructure.client.dto.request.CreateDeliveryRequest;
import com.winner.orderservice.order.infrastructure.client.dto.response.DeliveryResponse;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "delivery-service", url = "${feign.delivery-service.url:}")
public interface DeliveryFeignClient {

  @PostMapping("/internal/v1/delivery")
  ApiResponse<DeliveryResponse> createDelivery(@RequestBody CreateDeliveryRequest request);

  @PatchMapping("/api/v1/deliveries/{deliveryId}/cancelled")
  ApiResponse<Void> cancelDelivery(
      @PathVariable("deliveryId") UUID deliveryId,
      @RequestHeader("X-User-Role") String userRole
  );
}
