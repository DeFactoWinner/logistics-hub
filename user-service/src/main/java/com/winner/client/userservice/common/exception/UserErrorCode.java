package com.winner.client.userservice.common.exception;

import com.winner.client.global.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {
  INVALID_INPUT_VALUE("USER_4000", HttpStatus.BAD_REQUEST, "입력값이 올바르지 않습니다"),
  LOGIN_FAILED("USER_4011", HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 일치하지 않습니다."),
  USER_NOT_APPROVED("USER_4031", HttpStatus.FORBIDDEN, "아직 승인되지 않은 사용자입니다."),
  USER_DELETED("USER_4030", HttpStatus.GONE, "삭제된 사용자입니다."),

  INVALID_TOKEN("USER_4010", HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
  EXPIRED_TOKEN("USER_4012", HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
  TOKEN_BLACKLISTED("USER_4033", HttpStatus.FORBIDDEN, "로그아웃된 토큰입니다."),

  USER_NOT_FOUND("USER_4040", HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
  DUPLICATE_USERNAME("USER_4090", HttpStatus.CONFLICT, "이미 사용 중인 아이디입니다."),
  DUPLICATE_PHONE_NUMBER("USER_4091", HttpStatus.CONFLICT, "이미 사용 중인 전화번호입니다."),
  DUPLICATE_SLACK_ID("USER_4093", HttpStatus.CONFLICT, "이미 사용 중인 슬랙 ID입니다."),

  INVALID_USER_STATUS_CHANGE("USER_4001", HttpStatus.BAD_REQUEST, "변경할 수 없는 유저 상태입니다."),
  ACTIVE_USER_CANNOT_WITHDRAW("USER_4032", HttpStatus.FORBIDDEN, "진행 중인 업무가 있어 탈퇴할 수 없습니다."),

  INVALID_ROLE("USER_4000", HttpStatus.BAD_REQUEST, "유효하지 않은 역할 권한입니다."),
  FORBIDDEN_ACCESS("USER_4034", HttpStatus.FORBIDDEN, "해당 리소스에 접근할 권한이 없습니다."),
  MISSING_REFERENCE_ID("USER_4002", HttpStatus.BAD_REQUEST, "해당 권한은 소속 ID가 필수입니다.");

  private final String code;
  private final HttpStatus status;
  private final String message;

  public String format(Object... args) {
    return String.format(this.message, args);
  }
}
