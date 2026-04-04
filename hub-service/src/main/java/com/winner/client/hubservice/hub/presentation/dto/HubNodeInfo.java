package com.winner.client.hubservice.hub.presentation.dto;

import java.util.UUID;

public record HubNodeInfo(
    UUID fromHubId,
    String fromHubName,
    UUID toHubId,
    String toHubName,
    int sequence,
    double estimatedDistance,
    double estimatedArrivalTime
) {}
