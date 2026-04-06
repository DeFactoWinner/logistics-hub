package com.winner.orderservice.order.infrastructure.client;

import com.winner.client.global.response.ApiResponse;
import com.winner.orderservice.order.infrastructure.client.dto.response.ProductResponse;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", url = "${feign.product-service.url:}")
public interface ProductFeignClient {

  @GetMapping("/api/v1/products/{productId}")
  ApiResponse<ProductResponse> getProduct(@PathVariable("productId") UUID productId);

}
