package com.winner.orderservice.order.infrastructure.client.dto.response;

import java.util.UUID;

public record CompanyResponse(
    UUID companyId,
    String name,
    String address,
    String addressDetail,
    String type,
    UUID hubId
) {}
