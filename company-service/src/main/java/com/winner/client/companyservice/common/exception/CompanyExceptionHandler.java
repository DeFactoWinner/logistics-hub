package com.winner.client.companyservice.common.exception;

import com.winner.client.global.exception.BusinessException;
import com.winner.client.global.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CompanyExceptionHandler {

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
    return ResponseEntity
        .status(HttpStatus.CONFLICT)
        .body(ApiResponse.error(CompanyErrorCode.DUPLICATE_COMPANY));
  }

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException e) {
    return ResponseEntity
        .status(e.getErrorCode().getStatus())
        .body(ApiResponse.error(e.getErrorCode()));
  }

}
