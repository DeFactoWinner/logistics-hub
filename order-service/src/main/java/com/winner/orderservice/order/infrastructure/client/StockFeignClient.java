package com.winner.orderservice.order.infrastructure.client;

import com.winner.client.global.response.ApiResponse;
import com.winner.orderservice.order.infrastructure.client.dto.request.UpdateStockRequest;
import com.winner.orderservice.order.infrastructure.client.dto.response.StockResultResponse;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "stock-service", url = "${feign.product-service.url:}")
public interface StockFeignClient {

  @PatchMapping("/api/v1/stocks/products/{productId}")
  ApiResponse<StockResultResponse> updateProductStock(
      @PathVariable("productId") UUID productId,
      @RequestBody UpdateStockRequest request
  );
}
