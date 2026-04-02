package com.winner.client.deliveryservice.common.exception.deliverymanager.company;

import com.winner.client.global.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum DeliveryManagerCompanyErrorCode implements ErrorCode {
	ALREADY_REGISTERED_COMPANY_DELIVERY_MANAGER("ERROR_601", HttpStatus.CONFLICT,
		"이미 등록 된 업체 배송 담당자 입니다."),
	EXCEEDED_TO_IN_HUB_DELIVERY_MANAGER("ERROR_602", HttpStatus.CONFLICT,
		"허브 내에 배달담당자 TO 초과"),
	NOT_FOUND_COMPANY_DELIVERY_MANAGER("ERROR_603", HttpStatus.NOT_FOUND,
		"존재하지 않는 업체배송담당자 입니다."),
	DELIVERY_MANAGER_IN_PROGRESS("ERROR_604", HttpStatus.BAD_REQUEST,
		"업무 진행 중엔 탈퇴가 불가능 합니다.");

	private final String code;
	private final HttpStatus status;
	private final String message;
}
