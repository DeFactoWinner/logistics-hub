package com.winner.client.userservice.common.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

  private final UserErrorCode errorCode;

  public BusinessException(UserErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }
}
