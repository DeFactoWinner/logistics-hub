package com.winner.client.userservice.user.application.result;

import java.util.UUID;
import lombok.Builder;

@Builder
public record TokenResult(
    UUID userId,
    String accessToken,
    String refreshToken,
    Long expiresIn
) {

  public static TokenResult from(UUID userId, String accessToken, String refreshToken,
      Long expiresIn) {
    return TokenResult.builder()
        .userId(userId)
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .expiresIn(expiresIn)
        .build();
  }
}
