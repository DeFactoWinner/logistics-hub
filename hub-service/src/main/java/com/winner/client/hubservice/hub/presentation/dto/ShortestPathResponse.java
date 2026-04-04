package com.winner.client.hubservice.hub.presentation.dto;

import java.util.List;

public record ShortestPathResponse(
    int count,
    List<HubNodeResponse> nodes
) {}
