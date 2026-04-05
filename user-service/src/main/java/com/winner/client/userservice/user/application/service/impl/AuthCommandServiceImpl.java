package com.winner.client.userservice.user.application.service.impl;

import com.winner.client.global.config.jwt.JwtTokenProvider;
import com.winner.client.global.exception.BusinessException;
import com.winner.client.global.exception.JwtTokenErrorCode;
import com.winner.client.userservice.common.exception.UserErrorCode;
import com.winner.client.userservice.user.application.dto.command.LoginCommand;
import com.winner.client.userservice.user.application.dto.command.RefreshTokenCommand;
import com.winner.client.userservice.user.application.dto.command.SignupCommand;
import com.winner.client.userservice.user.application.dto.result.SignupResult;
import com.winner.client.userservice.user.application.dto.result.TokenResult;
import com.winner.client.userservice.user.application.service.AuthCommandService;
import com.winner.client.userservice.user.domain.entity.User;
import com.winner.client.userservice.user.domain.repository.UserRepository;
import com.winner.client.userservice.user.domain.vo.Password;
import com.winner.client.userservice.user.domain.vo.PhoneNumber;
import com.winner.client.userservice.user.domain.vo.UserRole;
import com.winner.client.userservice.user.infrastructure.repository.RedisTokenRepository;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional()
public class AuthCommandServiceImpl implements AuthCommandService {

  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final JwtTokenProvider jwtTokenProvider;
  private final RedisTokenRepository redisTokenRepository;

  @Transactional
  public SignupResult signup(SignupCommand command) {
    if (userRepository.existsByUserNameAndDeletedAtNull(command.userName())) {
      throw new BusinessException(UserErrorCode.DUPLICATE_USERNAME);
    }
    User user = User.create(
        command.userName(),
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

  @Override
  public TokenResult login(LoginCommand command) {
    User user = userRepository.findByUserNameAndDeletedAtNull(command.userName())
        .orElseThrow(() -> new BusinessException(UserErrorCode.USER_NOT_FOUND));

    if (!user.isCorrectPassword(command.password(), passwordEncoder)) {
      throw new BusinessException(UserErrorCode.LOGIN_FAILED);
    }
    if (!user.isApprove()) {
      throw new BusinessException(UserErrorCode.USER_NOT_APPROVED);
    }
    return createTokenResultByUser(user);
  }

  @Override
  public TokenResult refreshToken(RefreshTokenCommand command) {

    String refreshToken = command.refreshToken();
    if (!jwtTokenProvider.validateToken(refreshToken)) {
      throw new BusinessException(JwtTokenErrorCode.INVALID_TOKEN);
    }
    UUID userId = jwtTokenProvider.getUserId(refreshToken);
    String savedRefreshToken = redisTokenRepository.getRefreshToken(userId);

    if (userId == null || !Objects.equals(savedRefreshToken, refreshToken)) {
      throw new BusinessException(JwtTokenErrorCode.INVALID_TOKEN);
    }
    User user = userRepository.findByIdAndDeletedAtNull(userId)
        .orElseThrow(() -> new BusinessException(UserErrorCode.USER_NOT_FOUND));

    return createTokenResultByUser(user);
  }

  private TokenResult createTokenResultByUser(User user) {
    String accessToken = jwtTokenProvider.createAccessToken(
        user.getId(),
        user.getRoleName(),
        user.getReferenceId(),
        user.isActive());

    String refreshToken = jwtTokenProvider.createRefreshToken(user.getId());
    Long expiresIn = jwtTokenProvider.getRemainingTime(accessToken);

    redisTokenRepository.saveRefreshToken(user.getId(), refreshToken);
    return TokenResult.from(user.getId(), accessToken, refreshToken, expiresIn);
  }
}
