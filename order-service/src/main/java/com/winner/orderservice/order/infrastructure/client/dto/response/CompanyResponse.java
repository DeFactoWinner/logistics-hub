package com.winner.orderservice.order.infrastructure.client.dto.response;

import java.util.UUID;

public record CompanyResponse(
    UUID id,
    String name,
    String type,
    UUID hubId,
    String address,
    Double lat,
    Double lng
) {}
