package com.winner.client.userservice.user.application.service.impl;

import com.winner.client.global.exception.BusinessException;
import com.winner.client.userservice.common.exception.UserErrorCode;
import com.winner.client.userservice.user.application.command.SignupCommand;
import com.winner.client.userservice.user.application.result.SignupResult;
import com.winner.client.userservice.user.application.service.AuthCommandService;
import com.winner.client.userservice.user.domain.entity.User;
import com.winner.client.userservice.user.domain.repository.UserRepository;
import com.winner.client.userservice.user.domain.vo.Password;
import com.winner.client.userservice.user.domain.vo.PhoneNumber;
import com.winner.client.userservice.user.domain.vo.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthCommandServiceImpl implements AuthCommandService {

  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;

  @Transactional
  public SignupResult signup(SignupCommand command) {
    if (userRepository.findByUsernameAndDeletedAtNull(command.username())) {
      throw new BusinessException(UserErrorCode.DUPLICATE_USERNAME);
    }
    User user = User.create(
        command.username(),
        command.name(),
        new Password(passwordEncoder.encode(command.password())),
        new PhoneNumber(command.phoneNumber()),
        command.slackId(),
        new UserRole(
            command.role(),
            command.referenceId()
        )
    );
    return SignupResult.from(userRepository.save(user));
  }
}
