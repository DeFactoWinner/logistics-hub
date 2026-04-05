package com.winner.client.hubservice.hub.presentation.dto;

import java.util.List;

public record HubRoutePathResponse(
    List<HubNodeInfo> nodes,
    int count,
    double totalTime
) {}
