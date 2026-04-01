package com.winner.client.deliveryservice.common.exception.delivery;

import com.winner.client.global.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum DeliveryErrorCode implements ErrorCode {
  NOT_FOUND_DELIVERY("ERROR_501", HttpStatus.NOT_FOUND, "배송을 찾을 수 없습니다."),
  NOT_FOUND_DELIVERY_ROUTE("ERROR_502", HttpStatus.NOT_FOUND, "배송경로를 찾을 수 없습니다."),
  ;

  private final String code;
  private final HttpStatus status;
  private final String message;
}
