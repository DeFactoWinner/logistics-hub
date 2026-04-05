package com.winner.client.gateway.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.winner.client.global.code.ErrorCode;
import com.winner.client.global.exception.BusinessException;
import com.winner.client.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Order(-1)
@RequiredArgsConstructor
@Slf4j
public class GlobalErrorExceptionHandler implements ErrorWebExceptionHandler {

  private final ObjectMapper objectMapper;

  @Override
  public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
    if (exchange.getResponse().isCommitted()) {
      return Mono.error(ex);
    }

    ErrorCode errorCode = GatewayErrorCode.INTERNAL_ERROR;
    if (ex instanceof BusinessException ce) {
      errorCode = ce.getErrorCode();
    } else if (ex instanceof org.springframework.cloud.gateway.support.NotFoundException) {
      errorCode = GatewayErrorCode.SERVICE_UNAVAILABLE;
    } else if (ex instanceof org.springframework.web.server.ResponseStatusException rse) {
      if (rse.getStatusCode() == org.springframework.http.HttpStatus.NOT_FOUND) {
        errorCode = GatewayErrorCode.ROUTE_NOT_FOUND;
      }
    } else if (ex.getCause() instanceof java.net.ConnectException) {
      errorCode = GatewayErrorCode.GATEWAY_TIMEOUT;
    }
    exchange.getResponse().setStatusCode(errorCode.getStatus());

    exchange.getResponse().getHeaders()
        .setContentType(org.springframework.http.MediaType.APPLICATION_JSON);

    ApiResponse<Void> errorResponse = ApiResponse.error(errorCode);

    try {
      byte[] bytes = objectMapper.writeValueAsBytes(errorResponse);
      DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);

      return exchange.getResponse().writeWith(Mono.just(buffer));
    } catch (JsonProcessingException e) {
      return exchange.getResponse().setComplete();
    }
  }
}