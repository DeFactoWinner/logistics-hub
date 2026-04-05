package com.winner.client.userservice.user.application.service.impl;

import com.winner.client.global.config.jwt.JwtTokenProvider;
import com.winner.client.userservice.user.application.service.UserCommandService;
import com.winner.client.userservice.user.domain.repository.TokenRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService {

  private final TokenRepository tokenRepository;
  private final JwtTokenProvider jwtTokenProvider;

  @Override
  public Void logout(UUID userId, String accessToken) {
    tokenRepository.deleteRefreshToken(userId);
    long expiresIn = jwtTokenProvider.getRemainingTime(accessToken);
    tokenRepository.registerBlacklist(accessToken, expiresIn);
    return null;
  }
}
