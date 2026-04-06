package com.winner.client.hubservice.hub.application.dto;

import com.winner.client.hubservice.hub.presentation.dto.CreateHubRequest;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateHubCommand {

    private String name;
    private String address;
    private double lat;
    private double lng;

    public static CreateHubCommand from(CreateHubRequest request) {
        return CreateHubCommand.builder()
            .name(request.getName())
            .address(request.getAddress())
            .lat(request.getLat())
            .lng(request.getLng())
            .build();
    }
}
