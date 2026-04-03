package com.winner.client.userservice.user.application.service;

import com.winner.client.userservice.user.application.command.LoginCommand;
import com.winner.client.userservice.user.application.command.SignupCommand;
import com.winner.client.userservice.user.application.result.LoginResult;
import com.winner.client.userservice.user.application.result.SignupResult;

public interface AuthCommandService {

  SignupResult signup(SignupCommand command);

  LoginResult login(LoginCommand command);
}
