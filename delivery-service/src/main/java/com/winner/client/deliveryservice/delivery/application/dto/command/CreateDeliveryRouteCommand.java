package com.winner.client.deliveryservice.delivery.application.dto.command;

import com.winner.client.deliveryservice.delivery.application.dto.external.HubRouteInfo;
import com.winner.client.deliveryservice.delivery.domain.entity.Delivery;
import com.winner.client.deliveryservice.delivery.domain.vo.CurrentHubRoute;
import com.winner.client.deliveryservice.delivery.domain.vo.Distance;
import com.winner.client.deliveryservice.delivery.domain.vo.Duration;
import java.util.List;

public record CreateDeliveryRouteCommand(
    Delivery delivery,
    CurrentHubRoute currentHubRoute,
    String curHubName,
    String nextHubName,
    int sequence,
    Distance estimatedDistance,
    Duration estimatedArrivalTime
) {
  public static List<CreateDeliveryRouteCommand> of(
      Delivery delivery, HubRouteInfo hubRouteInfo
  ) {
    return hubRouteInfo.nodes().stream()
        .map(node -> new CreateDeliveryRouteCommand(
            delivery,
            new CurrentHubRoute(node.fromHubId(), node.toHubId()),
            node.fromHubName(),
            node.toHubName(),
            node.sequence(),
            new Distance(node.estimatedDistance()),
            new Duration(node.estimatedArrivalTime())
        ))
        .toList();
  }
}
