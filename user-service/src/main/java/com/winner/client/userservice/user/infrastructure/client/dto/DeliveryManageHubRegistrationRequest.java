package com.winner.client.userservice.user.infrastructure.client.dto;

import com.winner.client.userservice.user.domain.entity.User;
import java.util.UUID;
import lombok.Builder;

@Builder
public record DeliveryManageHubRegistrationRequest(
    UUID userId,
    String name
) {

  public static DeliveryManageHubRegistrationRequest from(User user) {
    return DeliveryManageHubRegistrationRequest.builder()
        .userId(user.getId())
        .name(user.getName())
        .build();
  }
}