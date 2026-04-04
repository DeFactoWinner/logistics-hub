package com.winner.client.hubservice.hub.presentation.dto;

import java.util.UUID;

public record HubNodeResponse(
    UUID hubId,
    String hubName,
    int sequence
) {}
