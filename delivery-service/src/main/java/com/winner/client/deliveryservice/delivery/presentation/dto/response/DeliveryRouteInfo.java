package com.winner.client.deliveryservice.delivery.presentation.dto.response;

import com.winner.client.deliveryservice.delivery.domain.entity.DeliveryRoute;
import com.winner.client.deliveryservice.delivery.domain.enums.DeliveryRouteStatus;
import java.math.BigDecimal;
import java.util.UUID;

public record DeliveryRouteInfo(
    UUID deliveryRouteId,
    UUID deliveryId,
    int seq,

    HubInfo currentHubInfo,
    HubInfo nextHubInfo,

    BigDecimal estimatedDistance,
    Integer estimatedArrivalTime,

    BigDecimal actualDistance,
    Integer actualArrivalTime,

    DeliveryRouteStatus status,
    UUID deliveryManagerId
) {

  public static DeliveryRouteInfo from(DeliveryRoute route) {
    return new DeliveryRouteInfo(
        route.getId(),
        route.getDeliveryId(),
        route.getSeq(),

        new HubInfo(route.getCurrentHubRoute().getCurHubId(), route.getCurHubName()),
        new HubInfo(route.getCurrentHubRoute().getNextHubId(), route.getNextHubName()),

        route.getEstimatedDistance().getKilometers(),
        route.getEstimatedArrivalTime().getMinutes(),

        route.getActualDistance() != null ? route.getActualDistance().getKilometers() : null,
        route.getActualArrivalTime() != null ? route.getActualArrivalTime().getMinutes() : null,

        route.getStatus(),
        route.getDeliveryManagerId()
    );
  }
}