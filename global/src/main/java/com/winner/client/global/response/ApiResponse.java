package com.winner.client.global.response;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ApiResponse<T> {
  private String code;
  private String message;
  private T data;
  private LocalDateTime timestamp;

  private ApiResponse(BaseCode baseCode, T data) {
    this.code = baseCode.getCode();
    this.message = baseCode.getMessage();
    this.data = data;
    this.timestamp = LocalDateTime.now();
  }

  private ApiResponse(BaseCode baseCode, String customMessage, T data) {
    this.code = baseCode.getCode();
    this.message = customMessage;
    this.data = data;
    this.timestamp = LocalDateTime.now();
  }

  public static <T> ApiResponse<T> success(BaseCode successCode, T data) {
    return new ApiResponse<>(successCode, data);
  }

  public static <T> ApiResponse<T> error(BaseCode errorCode) {
    return new ApiResponse<>(errorCode, null);
  }

  public static <T> ApiResponse<T> error(BaseCode baseCode, String customMessage) {
    return new ApiResponse<>(baseCode, customMessage, null);
  }
}
