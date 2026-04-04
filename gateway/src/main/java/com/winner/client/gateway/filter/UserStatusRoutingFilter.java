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
public class UserStatusRoutingFilter implements GlobalFilter, Ordered {

  private final AuthProperties authProperties;
  private final AntPathMatcher pathMatcher = new AntPathMatcher();

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    String path = exchange.getRequest().getURI().getPath();

    String statusHeader = exchange.getRequest().getHeaders().getFirst("X-User-Status");

    if ("false".equalsIgnoreCase(statusHeader)) {
      boolean isAllowed = authProperties.getStatusAllowList().stream()
          .anyMatch(p -> pathMatcher.match(p.trim(), path));

      if (!isAllowed) {
        return Mono.error(new BusinessException(GatewayErrorCode.INTERNAL_ERROR));
      }
    }

    return chain.filter(exchange);
  }

  @Override
  public int getOrder() {
    return 2;
  }
}