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

  ACCESS_DENIED_HUB_ADMIN("ERROR_503", HttpStatus.FORBIDDEN, "담당 허브의 경로만 변경 가능합니다."),
  ACCESS_DENIED_DELIVERY_MANAGER("ERROR_504", HttpStatus.FORBIDDEN, "본인 담당 경로만 변경 가능합니다."),
  ACCESS_DENIED_ROLE("ERROR_505", HttpStatus.FORBIDDEN, "해당 작업을 수행할 권한이 없습니다."),

  NOT_ALL_ROUTES_COMPLETED("ERROR_508", HttpStatus.BAD_REQUEST, "하위 배송 경로가 모두 완료되지 않아 배송을 완료할 수 없습니다."),
  ;

  private final String code;
  private final HttpStatus status;
  private final String message;
}
