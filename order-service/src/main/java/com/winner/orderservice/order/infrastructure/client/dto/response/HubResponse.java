package com.winner.orderservice.order.infrastructure.client.dto.response;

import java.util.UUID;

public record HubResponse(
    UUID id,
    String name,
    String address,
    double lat,
    double lng
) {}
