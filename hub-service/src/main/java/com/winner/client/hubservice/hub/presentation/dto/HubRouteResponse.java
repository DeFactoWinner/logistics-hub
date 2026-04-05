package com.winner.client.hubservice.hub.presentation.dto;

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
}
