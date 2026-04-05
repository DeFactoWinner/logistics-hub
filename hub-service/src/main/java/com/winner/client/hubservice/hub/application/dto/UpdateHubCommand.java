package com.winner.client.hubservice.hub.application.dto;

import lombok.Getter;

@Getter
public class UpdateHubCommand {

    private String name;
    private String address;
    private double lat;
    private double lng;

    public UpdateHubCommand(String name, String address, double lat, double lng) {
        this.name = name;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
    }
}
