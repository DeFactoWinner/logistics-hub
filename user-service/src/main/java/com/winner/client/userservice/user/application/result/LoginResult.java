package com.winner.client.userservice.user.application.result;

import java.util.UUID;

public record LoginResult(
    UUID userId,
    String accessToken,
    String refreshToken
) {

  public static LoginResult from(UUID userId, String accessToken, String refreshToken) {
    return new LoginResult(userId, accessToken, refreshToken);
  }
}