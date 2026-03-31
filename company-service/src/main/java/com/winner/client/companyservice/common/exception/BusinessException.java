package com.winner.client.companyservice.common.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

  private final CompanyErrorCode errorCode;
  public BusinessException(CompanyErrorCode errorCode) {

      super(errorCode.getMessage());
      this.errorCode = errorCode;
  }
}
