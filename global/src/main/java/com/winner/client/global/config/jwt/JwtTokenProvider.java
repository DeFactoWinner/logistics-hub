package com.winner.client.global.config.jwt;

import com.winner.client.global.exception.BusinessException;
import com.winner.client.global.exception.JwtTokenErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

  @Value("${jwt.secret}")
  private String secret;
  @Value("${jwt.access_expiration_time}")
  private Long accessExpirationTime;
  @Value("${jwt.refresh_expiration_time}")
  private Long refreshExpirationTime;
  private SecretKey secretKey;

  @PostConstruct
  public void init() {
    this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
  }

  public String createAccessToken(UUID userId, String role, UUID referenceId,boolean userStatus) {
    Date now = new Date();
    Date expirationDate = new Date(now.getTime() + accessExpirationTime);

    return Jwts.builder()
        .setSubject(String.valueOf(userId))
        .claim("role", role)
        .claim("referenceId", referenceId)
        .claim("status",userStatus)
        .setIssuedAt(now)
        .setExpiration(expirationDate)
        .signWith(secretKey, SignatureAlgorithm.HS256)
        .compact();
  }

  public long getRemainingTime(String token) {
    try {
      Date expiration = getClaims(token).getExpiration();
      long now = new Date().getTime();
      return expiration.getTime() - now;
    } catch (Exception e) {
      return 0;
    }
  }

  public String createRefreshToken(UUID userId) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + refreshExpirationTime);

    return Jwts.builder()
        .setSubject(String.valueOf(userId))
        .setIssuedAt(now)
        .setExpiration(expiryDate)
        .signWith(secretKey, SignatureAlgorithm.HS256)
        .compact();
  }

  public Claims getClaims(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(secretKey)
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
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
}
