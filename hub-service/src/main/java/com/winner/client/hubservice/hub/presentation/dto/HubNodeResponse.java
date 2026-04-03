package com.winner.client.hubservice.hub.presentation.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HubNodeResponse {

    private UUID hubId;
    private String hubName;
    private int sequence;
}
