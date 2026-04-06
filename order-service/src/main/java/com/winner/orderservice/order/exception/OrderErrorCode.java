package com.winner.orderservice.order.exception;

import com.winner.client.global.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum OrderErrorCode implements ErrorCode {

  ORDER_NOT_FOUND("ORDER_E801", HttpStatus.NOT_FOUND, "주문을 찾을 수 없습니다."),
  INVALID_ORDER_STATUS("ORDER_E802", HttpStatus.CONFLICT, "해당 상태에서는 처리할 수 없습니다."),
  INVALID_ORDER_STATUS_TRANSITION("ORDER_E803", HttpStatus.CONFLICT, "유효하지 않은 상태 전이입니다."),
  PRODUCT_NOT_FOUND("ORDER_E804", HttpStatus.NOT_FOUND, "상품을 찾을 수 없습니다."),
  COMPANY_NOT_FOUND("ORDER_E805", HttpStatus.NOT_FOUND, "업체를 찾을 수 없습니다."),
  OUT_OF_STOCK("ORDER_E806", HttpStatus.CONFLICT, "재고가 부족합니다."),
  INVALID_QUERY_PARAMETER("ORDER_E807", HttpStatus.BAD_REQUEST, "잘못된 조회 파라미터입니다."),
  ACCESS_DENIED("ORDER_E808", HttpStatus.FORBIDDEN, "해당 주문에 접근 권한이 없습니다."),
  DELIVERY_CREATE_FAILED("ORDER_E809", HttpStatus.INTERNAL_SERVER_ERROR, "배송 생성에 실패했습니다."),
  INVALID_ROLE("ORDER_E810", HttpStatus.FORBIDDEN, "해당 작업을 수행할 권한이 없습니다."),
  INVALID_INPUT("ORDER_E811", HttpStatus.BAD_REQUEST, "잘못된 입력입니다."),
  ORDER_CREATE_FAILED("ORDER_E812", HttpStatus.INTERNAL_SERVER_ERROR, "주문 생성에 실패했습니다."),
  USER_NOT_FOUND("ORDER_E813", HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
  HUB_NOT_FOUND("ORDER_E814", HttpStatus.NOT_FOUND, "허브를 찾을 수 없습니다.");

  private final String code;
  private final HttpStatus status;
  private final String message;
}
