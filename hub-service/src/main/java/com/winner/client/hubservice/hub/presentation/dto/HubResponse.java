package com.winner.client.hubservice.hub.presentation.dto;

import com.winner.client.hubservice.hub.application.dto.HubResult;
import com.winner.client.hubservice.hub.domain.entity.Hub;
import java.io.Serializable;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HubResponse implements Serializable {

    private UUID id;
    private String name;
    private String address;
    private double lat;
    private double lng;

    public static HubResponse from(HubResult result) {
        return HubResponse.builder()
            .id(result.getId())
            .name(result.getName())
            .address(result.getAddress())
            .lat(result.getLat())
            .lng(result.getLng())
            .build();
    }

    public static HubResponse from(Hub hub) {
        return HubResponse.builder()
            .id(hub.getId())
            .name(hub.getName())
            .address(hub.getLocation().getAddress())
            .lat(hub.getLocation().getLat())
            .lng(hub.getLocation().getLng())
            .build();
    }
}
