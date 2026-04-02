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
	;

	private final String code;
	private final HttpStatus status;
	private final String message;
}
