package com.winner.client.hubservice.hub.application.dto;

import com.winner.client.hubservice.hub.presentation.dto.CreateHubRouteRequest;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateRouteCommand {

    private UUID fromHubId;
    private UUID toHubId;
    private double distance;
    private int duration;

    public static CreateRouteCommand from(CreateHubRouteRequest request) {
        return CreateRouteCommand.builder()
            .fromHubId(request.getFromHubId())
            .toHubId(request.getToHubId())
            .distance(request.getDistance())
            .duration(request.getDuration())
            .build();
    }
}
