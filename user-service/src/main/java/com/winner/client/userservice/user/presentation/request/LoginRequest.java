package com.winner.client.userservice.user.presentation.request;

import com.winner.client.userservice.user.application.command.LoginCommand;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record LoginRequest(
    @Length(min = 4, max = 10)
    @NotBlank(message = "아이디는 필수입니다.")
    String userName,
    @Length(min = 8, max = 15)
    @NotBlank(message = "비밀번호는 필수입니다.")
    String password) {

  public static LoginCommand toCommand(LoginRequest request) {
    return new LoginCommand(request.userName, request.password);
  }
}
