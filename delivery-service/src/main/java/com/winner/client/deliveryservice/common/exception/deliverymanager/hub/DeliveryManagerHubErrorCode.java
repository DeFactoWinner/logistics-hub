package com.winner.client.deliveryservice.common.exception.deliverymanager.hub;

import com.winner.client.global.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum DeliveryManagerHubErrorCode implements ErrorCode {
	ALREADY_REGISTERED_HUB_DELIVERY_MANAGER("ERROR_651", HttpStatus.CONFLICT,
		"이미 등록 된 업체 배송 담당자 입니다."),
	HUB_DELIVERY_MANAGER_OVER_CAPACITY("ERROR_652", HttpStatus.CONFLICT,
		"허브 배송 담당자 정원 초과"),
	NOT_FOUND_HUB_DELIVERY_MANAGER("ERROR_653", HttpStatus.NOT_FOUND,
		"존재하지 않는 허브배송담당자 입니다."),
	DELIVERY_MANAGER_IN_PROGRESS("ERROR_654", HttpStatus.BAD_REQUEST,
		"업무 진행 중엔 탈퇴가 불가능 합니다."),
	NOT_FOUND_AVAILABLE_HUB_DELIVERY_MANAGERS("ERROR_655", HttpStatus.NOT_FOUND,
		"배정 가능한 허브 배송담당자를 찾을 수 없습니다."),
	USER_ID_CANNOT_BE_NULL("ERROR_656", HttpStatus.BAD_REQUEST,
		"유저 아이디는 null 일 수 없습니다."),
	USER_NAME_CANNOT_BE_NULL("ERROR_657", HttpStatus.BAD_REQUEST,
		"유저 이름은 null 일 수 없습니다."),
	NOT_AVAILABLE("ERROR_658", HttpStatus.CONFLICT,
		"배송 가능상태가 아닙니다.")
	;


	private final String code;
	private final HttpStatus status;
	private final String message;
}
