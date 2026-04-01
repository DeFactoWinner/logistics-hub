package com.winner.client.deliveryservice.delivery.presentation.dto.response;

import com.winner.client.deliveryservice.delivery.domain.entity.DeliveryRoute;
import java.util.List;

public record ListDeliveryRouteResponse(
    List<DeliveryRouteInfo> routes, int routesCount){
  public static ListDeliveryRouteResponse from(List<DeliveryRoute> routes) {
    List<DeliveryRouteInfo> routeInfos = routes.stream()
        .map(DeliveryRouteInfo::from)
        .toList();

    return new ListDeliveryRouteResponse(
        routeInfos,
        routeInfos.size()
    );
  }
}
