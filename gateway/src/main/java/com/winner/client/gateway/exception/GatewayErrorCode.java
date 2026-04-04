package com.winner.client.gateway.exception;

import com.winner.client.global.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum GatewayErrorCode implements ErrorCode {
  INVALID_ROLE("ERROR_400", HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
  ROUTE_NOT_FOUND("ERROR_401", HttpStatus.NOT_FOUND, "요청 경로를 찾을 수 없습니다."),
  INTERNAL_ERROR("ERROR-402", HttpStatus.INTERNAL_SERVER_ERROR, "게이트웨이 오류");
  private final String code;
  private final HttpStatus status;
  private final String message;
}