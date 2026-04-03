package com.winner.client.userservice.user.presentation.response;

import com.winner.client.userservice.user.application.result.LoginResult;
import java.util.UUID;

public record LoginResponse(
    UUID userId,
    String accessToken,
    String refreshToken
) {

  public static LoginResponse from(LoginResult result) {
    return new LoginResponse(
        result.userId(), result.accessToken(), result.refreshToken()
    );
  }
}
