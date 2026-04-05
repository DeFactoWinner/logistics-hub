package com.winner.client.productservice.common.exception;

import com.winner.client.global.code.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum StockErrorCode implements ErrorCode {

  STOCK_NOT_FOUND("ERROR_951", HttpStatus.NOT_FOUND,"재고 데이터가 존재하지 않습니다."),
  PRODUCT_NOT_FOUND("ERROR_952", HttpStatus.NOT_FOUND,"존재하지 않는 제품입니다."),

  INSUFFICIENT_STOCK("ERROR_953", HttpStatus.CONFLICT, "재고가 부족합니다."),

  INVALID_STOCK_QUANTITY("ERROR_954", HttpStatus.BAD_REQUEST, "재고 수량은 0 미만일 수 없습니다."),

  INVALID_STOCK_UPDATE_AMOUNT("ERROR_955", HttpStatus.BAD_REQUEST, "변경할 재고 수량은 0일 수 없습니다."),
  PRODUCT_ID_REQUIRED("ERROR_903", HttpStatus.BAD_REQUEST, "허브 ID는 필수 입력값입니다.");

  private final String code;
  private final HttpStatus status;
  private final String message;

  StockErrorCode(String code, HttpStatus status, String message) {
    this.code = code;
    this.status = status;
    this.message = message;
  }
}
