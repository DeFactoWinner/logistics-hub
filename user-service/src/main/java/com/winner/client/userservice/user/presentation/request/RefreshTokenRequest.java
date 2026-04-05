package com.winner.client.userservice.user.presentation.request;

import com.winner.client.userservice.user.application.command.RefreshTokenCommand;
import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(
    @NotBlank(message = "리프레시 토큰은 필수입니다.")
    String refreshToken) {

  public static RefreshTokenCommand toCommand(RefreshTokenRequest request) {
    return new RefreshTokenCommand(request.refreshToken);
  }
}
