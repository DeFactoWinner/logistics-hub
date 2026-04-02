package com.winner.client.hubservice.hub.presentation.dto;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HubResponse {

    private UUID id;
    private String name;
    private String address;
    private double lat;
    private double lng;
}
