package com.winner.orderservice.order.infrastructure.client;

import com.winner.client.global.response.ApiResponse;
import com.winner.orderservice.order.infrastructure.client.dto.response.UserDetailResponse;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "${feign.user-service.url:}")
public interface UserFeignClient {

  @GetMapping("/internal/v1/users/{userId}")
  ApiResponse<UserDetailResponse> getUserDetails(@PathVariable("userId") UUID userId);
}
