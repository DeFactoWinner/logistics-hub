package com.winner.client.global.config.jwt;

import com.winner.client.global.exception.BusinessException;
import com.winner.client.global.exception.JwtTokenErrorCode;
import com.winner.client.global.variable.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(JwtProperties.class)
@ConditionalOnProperty(name = "app.jwt.secret")
public class JwtTokenProvider {

  private final JwtProperties jwtProperties;
  private SecretKey secretKey;

  @PostConstruct
  public void init() {
    this.secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
  }

  public String createAccessToken(UUID userId, String role, UUID referenceId, boolean userStatus) {
    Date now = new Date();
    Date expirationDate = new Date(now.getTime() + jwtProperties.getAccessExpirationTime());

    return Jwts.builder()
        .subject(String.valueOf(userId))
        .claim("role", role)
        .claim("referenceId", referenceId)
        .claim("status", userStatus)
        .issuedAt(now)
        .expiration(expirationDate)
        .signWith(secretKey)
        .compact();
  }

  public long getRemainingTime(String token) {
    try {
      Date expiration = getClaims(token).getExpiration();
      long now = new Date().getTime();
      return Math.max(0, expiration.getTime() - now);
    } catch (Exception e) {
      return 0;
    }
  }

  public String createRefreshToken(UUID userId) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + jwtProperties.getAccessExpirationTime());

    return Jwts.builder()
        .subject(String.valueOf(userId))
        .issuedAt(now)
        .expiration(expiryDate)
        .signWith(secretKey)
        .compact();
  }

  public Claims getClaims(String token) {
    return Jwts.parser()
        .verifyWith(secretKey)
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser()
          .verifyWith(secretKey)
          .build()
          .parseSignedClaims(token);
      return true;
    } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
      throw new BusinessException(JwtTokenErrorCode.INVALID_SIGNATURE);
    } catch (ExpiredJwtException e) {
      throw new BusinessException(JwtTokenErrorCode.EXPIRED_TOKEN);
    } catch (UnsupportedJwtException e) {
      throw new BusinessException(JwtTokenErrorCode.UNSUPPORTED_TOKEN);
    } catch (IllegalArgumentException e) {
      throw new BusinessException(JwtTokenErrorCode.EMPTY_CLAIMS);
    }
  }

  public UUID getUserId(String token) {
    return UUID.fromString(getClaims(token).getSubject());
  }
}