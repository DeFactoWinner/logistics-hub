package com.winner.client.deliveryservice.delivery.presentation.dto.response;

import com.winner.client.deliveryservice.delivery.application.dto.result.FindDeliveryRouteResult;
import java.math.BigDecimal;
import java.util.UUID;

public record GetDeliveryRouteResponse(
    UUID deliveryRouteId,
    UUID deliveryId,
    int seq,

    UUID curHubId,
    String curHubName,
    UUID nextHubId,
    String nextHubName,

    BigDecimal estimatedDistance,
    double estimatedArrivalTime,

    BigDecimal actualDistance,
    double actualArrivalTime,

    String status,
    String deliveryManagerName
) {
  public static GetDeliveryRouteResponse from(FindDeliveryRouteResult query) {
    return new GetDeliveryRouteResponse(
        query.deliveryRouteId(),
        query.deliveryId(),
        query.seq(),

        query.currentHubRoute().getCurHubId(),
        query.curHubName(),
        query.currentHubRoute().getNextHubId(),
        query.nextHubName(),

        query.estimatedDistance().getKilometers(),
        query.estimatedArrivalTime().getMinutes(),

        query.actualDistance() != null ? query.actualDistance().getKilometers() : null,
        query.actualArrivalTime() != null ? query.actualArrivalTime().getMinutes() : null,

        query.status().name(),
        query.deliveryManagerName()
    );
  }
}