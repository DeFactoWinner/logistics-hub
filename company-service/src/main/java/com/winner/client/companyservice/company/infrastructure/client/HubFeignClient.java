package com.winner.client.companyservice.company.infrastructure.client;

import com.winner.client.companyservice.company.infrastructure.client.dto.response.HubResponse;
import com.winner.client.global.response.ApiResponse;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "hub-service")
public interface HubFeignClient {

  @GetMapping("/api/v1/hubs/{hubId}")
  HubResponse getHub(@PathVariable(value = "hubId") UUID hubId);



}
