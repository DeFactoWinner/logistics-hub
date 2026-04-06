package com.winner.client.userservice.common.exception;

import com.winner.client.global.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {
  INVALID_INPUT_VALUE("ERROR_200", HttpStatus.BAD_REQUEST, "입력값이 올바르지 않습니다"),
  LOGIN_FAILED("ERROR_201", HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 일치하지 않습니다."),
  USER_NOT_APPROVED("ERROR_202", HttpStatus.FORBIDDEN, "아직 승인되지 않은 사용자입니다."),
  ALREADY_USER_APPROVED("ERROR_202", HttpStatus.FORBIDDEN, "이미 승인된 사용자입니다."),
  USER_DELETED("ERROR_203", HttpStatus.GONE, "삭제된 사용자입니다."),

  INVALID_USER_STATUS_CHANGE("ERROR_204", HttpStatus.BAD_REQUEST, "변경할 수 없는 유저 상태입니다."),
  ACTIVE_USER_CANNOT_WITHDRAW("ERROR_205", HttpStatus.FORBIDDEN, "진행 중인 업무가 있어 탈퇴할 수 없습니다."),

  USER_NOT_FOUND("ERROR_206", HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
  DUPLICATE_USERNAME("ERROR_207", HttpStatus.CONFLICT, "이미 사용 중인 아이디입니다."),
  DUPLICATE_PHONE_NUMBER("ERROR_208", HttpStatus.CONFLICT, "이미 사용 중인 전화번호입니다."),
  DUPLICATE_SLACK_ID("ERROR_209", HttpStatus.CONFLICT, "이미 사용 중인 슬랙 ID입니다."),

  INVALID_ROLE("ERROR_210", HttpStatus.BAD_REQUEST, "유효하지 않은 역할 권한입니다."),
  FORBIDDEN_ACCESS("ERROR_211", HttpStatus.FORBIDDEN, "해당 리소스에 접근할 권한이 없습니다."),
  MISSING_REFERENCE_ID("ERROR_212", HttpStatus.BAD_REQUEST, "해당 권한은 소속 ID가 필수입니다.");

  private final String code;
  private final HttpStatus status;
  private final String message;

  public String format(Object... args) {
    return String.format(this.message, args);
  }
}
