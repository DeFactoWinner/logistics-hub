package com.winner.client.userservice.user.infrastructure.repository;

import com.winner.client.global.variable.JwtProperties;
import com.winner.client.userservice.user.domain.repository.TokenRepository;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RedisTokenRepository implements TokenRepository {

  private static final String RT_PREFIX = "RT:";
  private static final String BL_PREFIX = "BL:";
  private final StringRedisTemplate redisTemplate;
  private final JwtProperties jwtProperties;

  @Override
  public void registerBlacklist(String token, long expiration) {
    redisTemplate.opsForValue().set(
        BL_PREFIX + token,
        "logout",
        expiration,
        TimeUnit.MILLISECONDS
    );
  }

  @Override
  public String getRefreshToken(UUID userId) {
    return redisTemplate.opsForValue().get(RT_PREFIX + userId.toString());
  }

  @Override
  public void saveRefreshToken(UUID userId, String token) {
    redisTemplate.opsForValue().set(
        RT_PREFIX + userId.toString(),
        token,
        jwtProperties.getRefreshExpirationTime(),
        TimeUnit.SECONDS
    );
  }

  @Override
  public void deleteRefreshToken(UUID userId) {
    redisTemplate.delete(RT_PREFIX + userId.toString());
  }
}
