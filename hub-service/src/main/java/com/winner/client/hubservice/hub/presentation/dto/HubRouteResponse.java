package com.winner.client.hubservice.hub.presentation.dto;

import com.winner.client.hubservice.hub.application.dto.HubRouteResult;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HubRouteResponse {

    private UUID id;
    private UUID fromHubId;
    private UUID toHubId;
    private double distance;
    private double duration;

    public static HubRouteResponse from(HubRouteResult result) {
        return HubRouteResponse.builder()
            .id(result.getId())
            .fromHubId(result.getFromHubId())
            .toHubId(result.getToHubId())
            .distance(result.getDistance())
            .duration(result.getDuration())
            .build();
    }
}
