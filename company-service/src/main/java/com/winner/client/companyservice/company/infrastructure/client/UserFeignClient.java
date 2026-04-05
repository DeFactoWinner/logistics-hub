package com.winner.client.companyservice.company.infrastructure.client;

import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserFeignClient {

  @PatchMapping("/internal/v1/users/{userid}/unassign")
  void unassignUser(@PathVariable UUID userid);
}
