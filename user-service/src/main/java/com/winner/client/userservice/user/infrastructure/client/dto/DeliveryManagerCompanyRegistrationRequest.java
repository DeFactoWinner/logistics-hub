package com.winner.client.userservice.user.infrastructure.client.dto;

import com.winner.client.userservice.user.domain.entity.User;
import java.util.UUID;
import lombok.Builder;

@Builder
public record DeliveryManagerCompanyRegistrationRequest(
    UUID userId,
    UUID hubId,
    String name
) {

  public static DeliveryManagerCompanyRegistrationRequest from(User user) {
    return DeliveryManagerCompanyRegistrationRequest.builder()
        .userId(user.getId())
        .hubId(user.getReferenceId())
        .name(user.getName())
        .build();
  }
}
