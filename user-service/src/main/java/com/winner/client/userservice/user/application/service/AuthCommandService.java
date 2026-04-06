package com.winner.client.userservice.user.application.service;

import com.winner.client.userservice.user.application.dto.command.LoginCommand;
import com.winner.client.userservice.user.application.dto.command.RefreshTokenCommand;
import com.winner.client.userservice.user.application.dto.command.SignupCommand;
import com.winner.client.userservice.user.application.dto.result.SignupResult;
import com.winner.client.userservice.user.application.dto.result.TokenResult;

public interface AuthCommandService {

  SignupResult signup(SignupCommand command);

  TokenResult login(LoginCommand command);

  TokenResult refreshToken(RefreshTokenCommand command);
}
