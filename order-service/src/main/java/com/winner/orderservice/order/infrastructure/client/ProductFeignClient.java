package com.winner.orderservice.order.infrastructure.client;

import com.winner.client.global.response.ApiResponse;
import com.winner.orderservice.order.infrastructure.client.dto.request.ModifyStockRequest;
import com.winner.orderservice.order.infrastructure.client.dto.response.ProductResponse;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "product-service", url = "${feign.product-service.url:}")
public interface ProductFeignClient {

  @GetMapping("/api/v1/products/{productId}")
  ApiResponse<ProductResponse> getProduct(@PathVariable UUID productId);

  @PatchMapping("/api/v1/products/{productId}/stocks")
  ApiResponse<Void> modifyStock(@PathVariable UUID productId, @RequestBody ModifyStockRequest request);
}
