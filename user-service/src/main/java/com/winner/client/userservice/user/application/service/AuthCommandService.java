package com.winner.client.userservice.user.application.service;

import com.winner.client.userservice.user.application.command.LoginCommand;
import com.winner.client.userservice.user.application.command.RefreshTokenCommand;
import com.winner.client.userservice.user.application.command.SignupCommand;
import com.winner.client.userservice.user.application.result.SignupResult;
import com.winner.client.userservice.user.application.result.TokenResult;

public interface AuthCommandService {

  SignupResult signup(SignupCommand command);

  TokenResult login(LoginCommand command);

  TokenResult refreshToken(RefreshTokenCommand command);
}
