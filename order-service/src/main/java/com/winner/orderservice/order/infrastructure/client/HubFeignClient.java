package com.winner.orderservice.order.infrastructure.client;

import com.winner.orderservice.order.infrastructure.client.dto.response.HubResponse;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "hub-service", url = "${feign.hub-service.url:}")
public interface HubFeignClient {

  @GetMapping("/internal/v1/hubs/{hubId}")
  HubResponse getHub(@PathVariable("hubId") UUID hubId);
}
