package com.winner.client.global.exception;

import com.winner.client.global.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum JwtTokenErrorCode implements ErrorCode {
  INVALID_SIGNATURE("ERROR_301", HttpStatus.UNAUTHORIZED, "잘못된 JWT 서명입니다."),
  EXPIRED_TOKEN("ERROR_302", HttpStatus.UNAUTHORIZED, "만료된 JWT 토큰입니다."),
  UNSUPPORTED_TOKEN("ERROR_303", HttpStatus.UNAUTHORIZED, "지원되지 않는 JWT 토큰입니다."),
  EMPTY_CLAIMS("ERROR_304", HttpStatus.BAD_REQUEST, "JWT 토큰이 비어있거나 잘못되었습니다."),
  INVALID_TOKEN("ERROR_305", HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
  TOKEN_BLACKLISTED("ERROR_306", HttpStatus.FORBIDDEN, "로그아웃된 토큰입니다.");

  private final String code;
  private final HttpStatus status;
  private final String message;
}