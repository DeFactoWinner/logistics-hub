package com.winner.client.deliveryservice.delivery.infrastructure.client;

import com.winner.client.deliveryservice.delivery.infrastructure.client.dto.HubRouteResponse;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="hub-service")
public interface HubClient {
  @GetMapping("/internal/v1/hub-routes/shortest")
  HubRouteResponse getHubRoutes(@RequestParam UUID fromHubId, @RequestParam UUID toHubId);
}
