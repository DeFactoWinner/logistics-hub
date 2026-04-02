package com.winner.client.global.exception;

import com.winner.client.global.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {
  INVALID_INPUT("ERROR_001", HttpStatus.BAD_REQUEST, "잘못된 입력입니다."),
  UNAUTHORIZED("ERROR_002", HttpStatus.UNAUTHORIZED, "인증이 필요합니다."),
  FORBIDDEN("ERROR_003", HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
  NOT_FOUND("ERROR_004", HttpStatus.NOT_FOUND, "리소스를 찾을 수 없습니다."),
  INTERNAL_SERVER_ERROR("ERROR_005", HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다."),
  METHOD_NOT_ALLOWED("ERROR_006", HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않는 메서드입니다."),
  ;

  private final String code;
  private final HttpStatus status;
  private final String message;
}