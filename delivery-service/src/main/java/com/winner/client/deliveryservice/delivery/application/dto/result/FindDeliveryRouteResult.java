package com.winner.client.deliveryservice.delivery.application.dto.result;

import com.winner.client.deliveryservice.delivery.domain.entity.DeliveryRoute;
import com.winner.client.deliveryservice.delivery.domain.enums.DeliveryRouteStatus;
import com.winner.client.deliveryservice.delivery.domain.vo.CurrentHubRoute;
import com.winner.client.deliveryservice.delivery.domain.vo.Distance;
import com.winner.client.deliveryservice.delivery.domain.vo.Duration;
import java.util.UUID;

public record FindDeliveryRouteResult(
    UUID deliveryRouteId,
    UUID deliveryId,
    int seq,

    CurrentHubRoute currentHubRoute,
    String curHubName,
    String nextHubName,

    Distance estimatedDistance,
    Duration estimatedArrivalTime,

    Distance actualDistance,
    Duration actualArrivalTime,

    DeliveryRouteStatus status,
    String deliveryManagerName
) {
  public static FindDeliveryRouteResult from(DeliveryRoute route) {
    return new FindDeliveryRouteResult(
        route.getId(),
        route.getDelivery().getId(),
        route.getSeq(),

        route.getCurrentHubRoute(),
        route.getCurHubName(),
        route.getNextHubName(),

        route.getEstimatedDistance(),
        route.getEstimatedArrivalTime(),

        route.getActualDistance(),
        route.getActualArrivalTime(),

        route.getStatus(),
        route.getDeliveryManagerName()
    );
  }
}