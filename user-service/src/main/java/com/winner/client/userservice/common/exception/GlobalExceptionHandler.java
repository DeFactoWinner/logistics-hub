package com.winner.client.userservice.common.exception;

import com.winner.client.global.exception.BusinessException;
import com.winner.client.global.exception.CommonErrorCode;
import com.winner.client.global.response.ApiResponse;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(BusinessException.class)
  protected ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException e) {
    return ResponseEntity
        .status(e.getErrorCode().getStatus())
        .body(ApiResponse.error(e.getErrorCode()));
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ApiResponse<Void>> handleHttpRequestMethodNotSupportedException(
      HttpRequestMethodNotSupportedException e) {
    return ResponseEntity
        .status(HttpStatus.METHOD_NOT_ALLOWED)
        .body(ApiResponse.error(CommonErrorCode.METHOD_NOT_ALLOWED));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<ApiResponse<Map<String, String>>> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException ex
  ) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getGlobalErrors().forEach(error ->
        errors.put(error.getObjectName(), error.getDefaultMessage())
    );
    ex.getBindingResult().getFieldErrors().forEach(error ->
        errors.put(error.getField(), error.getDefaultMessage())
    );
    CommonErrorCode code = CommonErrorCode.INVALID_INPUT;
    return ResponseEntity.status(code.getStatus()).body(ApiResponse.error(code, errors));
  }


  @ExceptionHandler(Exception.class)
  protected ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
    return ResponseEntity
        .status(CommonErrorCode.INTERNAL_SERVER_ERROR.getStatus())
        .body(ApiResponse.error(CommonErrorCode.INTERNAL_SERVER_ERROR));
  }
}