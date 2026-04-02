package com.winner.client.hubservice.hub.application.dto;

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
}
