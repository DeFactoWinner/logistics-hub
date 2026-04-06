package com.winner.client.gateway.filter;

import com.winner.client.gateway.exception.GatewayErrorCode;
import com.winner.client.global.config.jwt.JwtTokenProvider;
import com.winner.client.global.exception.BusinessException;
import com.winner.client.global.exception.JwtTokenErrorCode;
import io.jsonwebtoken.Claims;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.http.HttpHeaders;
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
    String path = exchange.getRequest().getPath().toString();
    log.info("[JWT 필터] 요청 시작 - Path: {}", path);

    String skipAuth = exchange.getRequest().getHeaders().getFirst("X-Auth-Skip");
    if ("true".equalsIgnoreCase(skipAuth)) {
      return chain.filter(exchange);
    }

    String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
    String token = extractToken(authHeader);

    if (token == null) {
      return Mono.error(new BusinessException(GatewayErrorCode.UNAUTHORIZED));
    }

    jwtTokenProvider.validateToken(token);

    return redisTemplate.hasKey("BL:" + token)
        .flatMap(isBlacklisted -> {
          if (Boolean.TRUE.equals(isBlacklisted)) {
            return Mono.error(new BusinessException(JwtTokenErrorCode.TOKEN_BLACKLISTED));
          }

          return getExchangeWithToken(exchange, token)
              .map(chain::filter)
              .orElseGet(() -> {
                return Mono.error(new BusinessException(JwtTokenErrorCode.UNSUPPORTED_TOKEN));
              });
        });
  }

  private String extractToken(String authHeader) {
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      return null;
    }
    return authHeader.substring(7);
  }

  private Optional<ServerWebExchange> getExchangeWithToken(ServerWebExchange exchange,
      String token) {
    try {
      Claims claims = jwtTokenProvider.getClaims(token);

      String userId = claims.getSubject();
      String userRole = claims.get("role", String.class);
      String userStatus = String.valueOf(claims.get("status"));
      String referenceId =
          claims.get("referenceId") != null ? String.valueOf(claims.get("referenceId")) : "";

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