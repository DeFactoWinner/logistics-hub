package com.winner.client.hubservice.hub.application.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateHubCommand {

    private String name;
    private String address;
    private double lat;
    private double lng;
}
