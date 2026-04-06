package com.winner.client.hubservice.hub.application.dto;

import com.winner.client.hubservice.hub.presentation.dto.UpdateHubRequest;
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

    public static UpdateHubCommand from(UpdateHubRequest request) {
        return new UpdateHubCommand(
            request.getName(),
            request.getAddress(),
            request.getLat(),
            request.getLng()
        );
    }
}
