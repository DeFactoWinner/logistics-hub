package com.winner.client.gateway.filter;

import com.winner.client.global.config.jwt.JwtTokenProvider;
import com.winner.client.global.exception.BusinessException;
import com.winner.client.global.exception.CommonErrorCode;
import com.winner.client.global.exception.JwtTokenErrorCode;
import io.jsonwebtoken.Claims;
import jakarta.ws.rs.core.HttpHeaders;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

  private final ReactiveStringRedisTemplate redisTemplate;
  private final JwtTokenProvider jwtTokenProvider;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
    String token = extractToken(authHeader);
    if (token == null) {
      return Mono.error(new BusinessException(CommonErrorCode.UNAUTHORIZED));
    }
    if (isValidToken(token)) {
      return Mono.error(new BusinessException(JwtTokenErrorCode.TOKEN_BLACKLISTED));
    }
    exchange = getExchangeWithToken(exchange, token).
        orElseThrow(() -> new BusinessException(JwtTokenErrorCode.UNSUPPORTED_TOKEN));
    return chain.filter(exchange);
  }

  private String extractToken(String authHeader) {
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      return null;
    }
    return authHeader.substring(7);
  }

  private boolean isValidToken(String token) {
    jwtTokenProvider.validateToken(token);
    return !Boolean.TRUE.equals(redisTemplate.hasKey("logout:" + token).block());
  }

  private Optional<ServerWebExchange> getExchangeWithToken(ServerWebExchange exchange,
      String token) {
    try {
      Claims claims = jwtTokenProvider.getClaims(token);

      String userId = claims.getSubject();
      String userRole = claims.get("role", String.class);
      String userStatus = String.valueOf(claims.get("userStatus"));
      String referenceId = String.valueOf(claims.get("referenceId"));

      return Optional.of(exchange.mutate()
          .request(builder -> builder
              .header("X-User-Status", userStatus)
              .header("X-User-Id", userId)
              .header("X-User-Role", userRole)
              .header("X-User-ReferenceId", referenceId)
          ).build());
    } catch (Exception e) {
      return Optional.empty();
    }
  }


  @Override
  public int getOrder() {
    return 1;
  }

}