package com.winner.client.companyservice.common.exception;

import com.winner.client.global.code.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CompanyErrorCode implements ErrorCode {

  COMPANY_NOT_FOUND("ERROR_100", HttpStatus.NOT_FOUND, "존재하지 않는 업체입니다."),
  DELETED_COMPANY("ERROR_101", HttpStatus.BAD_REQUEST, "이미 삭제된 업체입니다."),
  HUB_NOT_FOUND("ERROR_102", HttpStatus.NOT_FOUND, "존재하지 않는 허브입니다."),
  UNAUTHORIZED_ACCESS("ERROR_103", HttpStatus.FORBIDDEN, "해당 업체 정보에 접근 권한이 없습니다."),
  ADDRESS_REQUIRED("ERROR_104", HttpStatus.BAD_REQUEST, "주소는 필수 입력값입니다."),
  HUB_ID_REQUIRED("ERROR_105", HttpStatus.BAD_REQUEST,"허브 ID는 필수 입력값입니다."),

  DUPLICATE_COMPANY("ERROR_106", HttpStatus.CONFLICT, "이미 사용 중인 업체입니다."),

  INVALID_ADDRESS("ERROR_107", HttpStatus.BAD_REQUEST, "주소가 잘못되었습니다."),
  INVALID_LATITUDE("ERROR_108", HttpStatus.BAD_REQUEST, "잘못된 위도값입니다."),
  INVALID_LONGITUDE("ERROR_109", HttpStatus.BAD_REQUEST, "잘못된 경도값입니다."),
  ADDRESS_API_ERROR("ERROR_110", HttpStatus.INTERNAL_SERVER_ERROR, "주소 조회 서비스와 통신 중 오류가 발생했습니다."),
  ADDRESS_TRANSFORMATION_FAILED("ERROR_111", HttpStatus.BAD_REQUEST, "주소를 좌표로 변환하는 데 실패하였습니다.");

  private final String code;
  private final HttpStatus status;
  private final String message;

  CompanyErrorCode(String code, HttpStatus status, String message) {
    this.code = code;
    this.status = status;
    this.message = message;
  }

}
