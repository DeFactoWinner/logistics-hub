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
  INVALID_DELIVERY_STATUS_TRANSITION("ERROR_509", HttpStatus.BAD_REQUEST, "현재 상태에서 해당 단계로 변경할 수 없습니다."),
  CANNOT_CANCEL_ROUTE("ERROR_510", HttpStatus.BAD_REQUEST, "이미 배송이 진행 중이거나 완료된 경로는 취소할 수 없습니다."),
  CANNOT_CANCEL_DELIVERY("ERROR_511", HttpStatus.BAD_REQUEST, "이미 진행 중이거나 완료된 배송은 취소할 수 없습니다."),
  ALREADY_DELIVERY_ASSIGNED("ERROR_512", HttpStatus.CONFLICT, "이미 배송이 배정된 주문입니다."),

  INVALID_LATITUDE("ERROR_513", HttpStatus.BAD_REQUEST, "위도(latitude)는 -90 ~ 90 범위여야 합니다."),
  INVALID_LONGITUDE("ERROR_514", HttpStatus.BAD_REQUEST, "경도(longitude)는 -180 ~ 180 범위여야 합니다."),
  INVALID_DURATION("ERROR_515", HttpStatus.BAD_REQUEST, "소요 시간은 0보다 커야 합니다."),
  INVALID_DISTANCE("ERROR_516", HttpStatus.BAD_REQUEST, "거리는 0보다 커야 합니다."),
  SAME_HUB_NOT_ALLOWED("ERROR_517", HttpStatus.BAD_REQUEST, "현재 허브와 다음 허브는 동일할 수 없습니다."),
  FIELD_CANNOT_BE_EMPTY_ADDRESS("ERROR_518", HttpStatus.BAD_REQUEST, "주소 필드(도로명, 상세)는 비워둘 수 없습니다."),
  INVALID_SEQUENCE("ERROR_519", HttpStatus.BAD_REQUEST, "순번(sequence)은 0 이상이어야 합니다."),
  INVALID_ROLE("ERROR_520", HttpStatus.BAD_REQUEST, "검증되지 않은 역할 입니다."),
  ;

  private final String code;
  private final HttpStatus status;
  private final String message;
}
