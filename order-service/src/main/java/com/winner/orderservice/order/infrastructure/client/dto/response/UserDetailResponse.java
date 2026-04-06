package com.winner.orderservice.order.infrastructure.client.dto.response;

import java.util.UUID;

public record UserDetailResponse(
    UUID userId,
    String userName,
    String name,
    String slackId
) {}
