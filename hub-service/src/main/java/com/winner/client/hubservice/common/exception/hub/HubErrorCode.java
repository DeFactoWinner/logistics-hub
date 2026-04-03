package com.winner.client.hubservice.common.exception.hub;

import com.winner.client.global.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum HubErrorCode implements ErrorCode {

    HUB_NOT_FOUND("ERROR_700", HttpStatus.NOT_FOUND, "존재하지 않는 허브입니다."),
    DUPLICATE_HUB("ERROR_701", HttpStatus.CONFLICT, "이미 존재하는 허브입니다."),
    INVALID_HUB_ID("ERROR_702", HttpStatus.BAD_REQUEST, "유효하지 않은 허브 ID입니다."),
    INVALID_HUB_NAME("ERROR_703", HttpStatus.BAD_REQUEST, "허브 이름은 필수입니다.");

    private final String code;
    private final HttpStatus status;
    private final String message;
}
