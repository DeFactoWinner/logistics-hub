package com.winner.client.global.response;

import com.winner.client.global.code.BaseCode;
import com.winner.client.global.code.ErrorCode;
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

  public static <T> ApiResponse<T> success(CommonSuccessCode successCode, T data) {
    return new ApiResponse<>(successCode, data);
  }

  public static <T> ApiResponse<T> error(ErrorCode errorCode) {
    return new ApiResponse<>(errorCode, null);
  }

  public static <T> ApiResponse<T> error(ErrorCode errorCode, String customMessage) {
    return new ApiResponse<>(errorCode, customMessage, null);
  }
}
