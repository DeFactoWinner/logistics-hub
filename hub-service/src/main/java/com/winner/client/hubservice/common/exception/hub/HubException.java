package com.winner.client.hubservice.common.exception.hub;

import com.winner.client.global.exception.BusinessException;
import lombok.Getter;

@Getter
public class HubException extends BusinessException {

    public HubException(HubErrorCode errorCode) {
        super(errorCode);
    }
}
