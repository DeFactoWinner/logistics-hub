package com.winner.client.gateway.filter;

import com.winner.client.gateway.exception.GatewayErrorCode;
import com.winner.client.global.exception.BusinessException;
import com.winner.client.global.variable.AuthProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserValidatorFilter implements GlobalFilter, Ordered {

  private final AuthProperties authProperties;
  private final AntPathMatcher pathMatcher = new AntPathMatcher();

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    String path = exchange.getRequest().getURI().getPath();
    log.info("[권한 필터] 검증 시작 - Path: {}", path);

    String skipAuth = exchange.getRequest().getHeaders().getFirst("X-Auth-Skip");
    if ("true".equalsIgnoreCase(skipAuth)) {
      return chain.filter(exchange);
    }

    String userRole = exchange.getRequest().getHeaders().getFirst("X-User-Role");
    String statusHeader = exchange.getRequest().getHeaders().getFirst("X-User-Status");

    try {
      checkAccessControl(path, userRole);
    } catch (BusinessException e) {
      return Mono.error(e);
    }
    if (!"true".equalsIgnoreCase(statusHeader)) {
      boolean isStatusAllowed = authProperties.getStatusAllowList().stream()
          .anyMatch(p -> pathMatcher.match(p.trim(), path));

      if (!isStatusAllowed) {
        return Mono.error(new BusinessException(GatewayErrorCode.INVALID_ROLE));
      }
    }

    return chain.filter(exchange);
  }

  private void checkAccessControl(String path, String userRole) {
    authProperties.getAccessControl().stream()
        .filter(ac -> pathMatcher.match(ac.path(), path))
        .findFirst()
        .ifPresent(ac -> {
          if (ac.roles() != null && !ac.roles().contains(userRole)) {
            throw new BusinessException(GatewayErrorCode.INVALID_ROLE);
          }
        });
  }

  @Override
  public int getOrder() {
    return 2;
  }
}