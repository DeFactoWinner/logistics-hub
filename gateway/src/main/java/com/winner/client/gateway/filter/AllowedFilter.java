package com.winner.client.gateway.filter;

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

@Slf4j
@RequiredArgsConstructor
@Component
public class AllowedFilter implements GlobalFilter, Ordered {

  private final AuthProperties authProperties;
  private final AntPathMatcher pathMatcher = new AntPathMatcher();

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    String path = exchange.getRequest().getURI().getPath();
    boolean isWhiteList = authProperties.getWhiteList().stream()
        .anyMatch(p -> pathMatcher.match(p.trim(), path));

    if (isWhiteList) {
      ServerWebExchange mutatedExchange = exchange.mutate()
          .request(builder -> builder.header("X-Auth-Skip", "true"))
          .build();
      return chain.filter(mutatedExchange);
    }
    return chain.filter(exchange);
  }

  @Override
  public int getOrder() {
    return 0;
  }
}
