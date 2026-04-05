package com.winner.client.userservice.user.domain.repository;

import java.util.UUID;

public interface TokenRepository {

  void registerBlacklist(String token, long expiration);

  String getRefreshToken(UUID userId);

  void saveRefreshToken(UUID userId, String token);

  void deleteRefreshToken(UUID userId);
}
