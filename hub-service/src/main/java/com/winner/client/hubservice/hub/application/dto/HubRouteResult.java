package com.winner.client.hubservice.hub.application.dto;

import java.io.Serializable;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HubRouteResult implements Serializable {

    private UUID id;
    private UUID fromHubId;
    private UUID toHubId;
    private double distance;
    private double duration;
}
