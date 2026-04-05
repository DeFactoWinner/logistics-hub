package com.winner.client.productservice.common.exception;

import com.winner.client.global.code.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ProductErrorCode implements ErrorCode {

  PRODUCT_NOT_FOUND("ERROR_900", HttpStatus.NOT_FOUND, "상품이 존재하지 않습니다."),
  HUB_NOT_FOUND("ERROR_901", HttpStatus.NOT_FOUND, "존재하지 않는 허브입니다."),
  COMPANY_NOT_FOUND("ERROR_902", HttpStatus.NOT_FOUND, "존재하지 않는 업체입니다."),

  HUB_ID_REQUIRED("ERROR_903", HttpStatus.BAD_REQUEST, "허브 ID는 필수 입력값입니다."),
  COMPANY_ID_REQUIRED("ERROR_904", HttpStatus.BAD_REQUEST, "업체 ID는 필수 입력값입니다."),
  IDENTICAL_IDS_NOT_ALLOWED("ERROR_905", HttpStatus.BAD_REQUEST, "업체ID와 허브ID는 동일할 수 없습니다."),
  INVALID_PRODUCT_STATUS("ERROR_906", HttpStatus.BAD_REQUEST, "유효하지 않은 상품상태입니다."),
  DELETED_PRODUCT("ERROR_907", HttpStatus.BAD_REQUEST, "이미 삭제된 상품입니다."),

  ALREADY_EXISTS_PRODUCT("ERROR_908", HttpStatus.CONFLICT, "이미 존재하는 상품입니다.");

  private final String code;
  private final HttpStatus status;
  private final String message;

  ProductErrorCode(String code, HttpStatus status, String message) {
    this.code = code;
    this.status = status;
    this.message = message;
  }
}
