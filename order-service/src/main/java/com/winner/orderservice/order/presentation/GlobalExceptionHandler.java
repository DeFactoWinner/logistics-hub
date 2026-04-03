package com.winner.orderservice.order.presentation;

import com.winner.client.global.exception.BusinessException;
import com.winner.client.global.exception.CommonErrorCode;
import com.winner.client.global.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ApiResponse<?>> handleBusinessException(BusinessException e) {
    log.warn("BusinessException: {}", e.getMessage());
    return ResponseEntity.status(e.getErrorCode().getStatus())
        .body(ApiResponse.error(e.getErrorCode()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<?>> handleValidationException(MethodArgumentNotValidException e) {
    String message = e.getBindingResult().getFieldErrors().stream()
        .findFirst()
        .map(FieldError::getDefaultMessage)
        .orElse("입력값이 올바르지 않습니다.");
    return ResponseEntity.badRequest()
        .body(ApiResponse.error(CommonErrorCode.INVALID_INPUT, message));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<?>> handleException(Exception e) {
    log.error("Unexpected error", e);
    return ResponseEntity.internalServerError()
        .body(ApiResponse.error(CommonErrorCode.INTERNAL_SERVER_ERROR));
  }
}

