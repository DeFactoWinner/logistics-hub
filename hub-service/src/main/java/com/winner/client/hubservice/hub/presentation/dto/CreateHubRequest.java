package com.winner.client.hubservice.hub.presentation.dto;

import lombok.Getter;

@Getter
public class CreateHubRequest {

    private String name;
    private String address;
    private double lat;
    private double lng;
}
