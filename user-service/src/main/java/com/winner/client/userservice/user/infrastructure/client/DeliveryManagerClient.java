package com.winner.client.userservice.user.infrastructure.client;

import com.winner.client.userservice.user.infrastructure.client.dto.DeliveryManageHubRegistrationRequest;
import com.winner.client.userservice.user.infrastructure.client.dto.DeliveryManagerCompanyRegistrationRequest;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "delivery-service")
public interface DeliveryManagerClient {

  @PostMapping("/internal/v1/delivery-managers/company")
  Void registrationDeliveryCompanyManager(
      @RequestBody DeliveryManagerCompanyRegistrationRequest request);

  @PostMapping("/internal/v1/delivery-managers/hub")
  Void registrationDeliveryHubManager(
      @RequestBody DeliveryManageHubRegistrationRequest request);

  @DeleteMapping("/internal/v1/delivery-managers/company/{userId}")
  Void deleteDeliveryCompanyManger(@PathVariable UUID userId);

  @DeleteMapping("/internal/v1/delivery-managers/hub/{userId}")
  Void deleteDeliveryHubManger(@PathVariable UUID userId);

}
