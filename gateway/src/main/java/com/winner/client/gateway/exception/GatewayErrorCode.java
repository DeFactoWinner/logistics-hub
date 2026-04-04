package com.winner.client.gateway.exception;

import com.winner.client.global.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum GatewayErrorCode implements ErrorCode {
  UNAUTHORIZED("ERROR_401", HttpStatus.UNAUTHORIZED, "인증 정보가 유효하지 않습니다."),
  INVALID_ROLE("ERROR_402", HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),

  ROUTE_NOT_FOUND("ERROR_404", HttpStatus.NOT_FOUND, "요청 경로를 찾을 수 없습니다."),
  METHOD_NOT_ALLOWED("ERROR_405", HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 HTTP 메서드입니다."),

  SERVICE_UNAVAILABLE("ERROR_403", HttpStatus.SERVICE_UNAVAILABLE, "연결된 서비스를 찾을 수 없거나 점검 중입니다."),
  GATEWAY_TIMEOUT("ERROR_406", HttpStatus.GATEWAY_TIMEOUT, "서비스 응답 시간이 초과되었습니다."),

  INTERNAL_ERROR("ERROR_407", HttpStatus.INTERNAL_SERVER_ERROR, "게이트웨이 내부 오류가 발생했습니다.");
  private final String code;
  private final HttpStatus status;
  private final String message;
}