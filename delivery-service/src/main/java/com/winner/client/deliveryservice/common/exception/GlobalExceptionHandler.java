package com.winner.client.deliveryservice.common.exception;

import com.winner.client.global.exception.BusinessException;
import com.winner.client.global.exception.CommonErrorCode;
import com.winner.client.global.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(BusinessException.class)
  protected ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException e) {
    log.warn("BusinessException 발생: {}", e.getErrorCode().getMessage());
    return ResponseEntity
        .status(e.getErrorCode().getStatus())
        .body(ApiResponse.error(e.getErrorCode()));
  }


  @ExceptionHandler(Exception.class)
  protected ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
    log.error("Unhandled Exception: ", e);
    return ResponseEntity
        .status(CommonErrorCode.INTERNAL_SERVER_ERROR.getStatus())
        .body(ApiResponse.error(CommonErrorCode.INTERNAL_SERVER_ERROR));
  }
}
