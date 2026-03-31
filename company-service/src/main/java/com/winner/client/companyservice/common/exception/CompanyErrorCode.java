package com.winner.client.companyservice.common.exception;

import com.winner.client.global.response.BaseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
public enum CompanyErrorCode implements BaseCode {

  COMPANY_NOT_FOUND("COM_001", HttpStatus.NOT_FOUND, "존재하지 않는 업체입니다."),
  DELETED_COMPANY("COM_002", HttpStatus.BAD_REQUEST, "이미 삭제된 업체입니다."),
  HUB_NOT_FOUND("COM_003", HttpStatus.NOT_FOUND, "존재하지 않는 허브입니다."),
  UNAUTHORIZED_ACCESS("COM_004", HttpStatus.FORBIDDEN, "해당 업체 정보에 접근 권한이 없습니다."),
  ADDRESS_REQUIRED("COM_005", HttpStatus.BAD_REQUEST, "주소는 필수 입력값입니다."),
  HUD_ID_REQUIRED("COM_006", HttpStatus.BAD_REQUEST,"허브 ID는 필수 입력값입니다."),

  DUPLICATE_COMPANY_NAME("COM_07", HttpStatus.CONFLICT, "이미 사용 중인 업체명입니다."),

  INVALID_ADDRESS("COM_021", HttpStatus.BAD_REQUEST, "주소가 잘못되었습니다."),
  INVALID_LATITUDE("COM_022", HttpStatus.BAD_REQUEST, "잘못된 위도값입니다."),
  INVALID_LONGITUDE("COM_023", HttpStatus.BAD_REQUEST, "잘못된 경도값입니다."),
  ADDRESS_API_ERROR("COM_024", HttpStatus.INTERNAL_SERVER_ERROR, "주소 조회 서비스와 통신 중 오류가 발생했습니다."),
  ADDRESS_TRANSFORMATION_FAILED("COM_025", HttpStatus.BAD_REQUEST, "주소를 좌표로 변환하는 데 실패하였습니다.");

  private final String code;
  private final HttpStatus status;
  private final String message;

  CompanyErrorCode(String code, HttpStatus status, String message) {
    this.code = code;
    this.status = status;
    this.message = message;
  }

}
