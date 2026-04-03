package com.winner.client.deliveryservice.delivery.presentation.dto.response;

import com.winner.client.deliveryservice.delivery.domain.entity.DeliveryRoute;
import com.winner.client.deliveryservice.delivery.domain.enums.DeliveryRouteStatus;
import java.util.UUID;

public record DeliveryRouteInfoResponse(
    UUID deliveryRouteId,
    UUID deliveryId,
    int seq,
    String currentHubName,
    String nextHubName,
    DeliveryRouteStatus status,
    String DeliveryManagerName
){
  public static DeliveryRouteInfoResponse from(DeliveryRoute route) {
    return new DeliveryRouteInfoResponse(
        route.getId(),
        route.getDelivery().getId(),
        route.getSeq(),
        route.getCurHubName(),
        route.getNextHubName(),
        route.getStatus(),
        route.getDeliveryManagerName()
    );
  }
}
