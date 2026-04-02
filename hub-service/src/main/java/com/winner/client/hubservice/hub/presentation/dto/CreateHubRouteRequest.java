package com.winner.client.hubservice.hub.presentation.dto;

import java.util.UUID;
import lombok.Getter;

@Getter
public class CreateHubRouteRequest {

    private UUID fromHubId;
    private UUID toHubId;
    private double distance;
    private int duration;
}
