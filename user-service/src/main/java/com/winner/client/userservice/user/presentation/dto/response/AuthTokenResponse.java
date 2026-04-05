package com.winner.client.userservice.user.presentation.dto.response;

import com.winner.client.userservice.user.application.dto.result.TokenResult;
import java.util.UUID;
import lombok.Builder;

@Builder
public record AuthTokenResponse(
    UUID userId,
    String accessToken,
    String refreshToken,
    Long expiresIn
) {

  public static AuthTokenResponse from(TokenResult result) {
    return AuthTokenResponse.builder()
        .userId(result.userId())
        .accessToken(result.accessToken())
        .refreshToken(result.refreshToken())
        .expiresIn(result.expiresIn()).build();
  }
}
