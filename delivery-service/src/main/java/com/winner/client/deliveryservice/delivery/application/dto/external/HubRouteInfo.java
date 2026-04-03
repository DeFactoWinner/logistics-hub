package com.winner.client.deliveryservice.delivery.application.dto.external;

import com.winner.client.deliveryservice.delivery.infrastructure.client.dto.HubRouteResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record HubRouteInfo(
    List<Node> nodes,
    int count
) {
  public static HubRouteInfo from(HubRouteResponse response) {
    return new HubRouteInfo(
        response.nodes().stream()
            .map(Node::from)
            .toList(),
        response.count()
    );
  }

  public record Node(
      UUID fromHubId, String fromHubName,
      UUID toHubId, String toHubName,
      int sequence,
      BigDecimal estimatedDistance,
      int estimatedArrivalTime
  ) {
    public static Node from(HubRouteResponse.HubNodeInfo node) {
      return new Node(
          node.fromHubId(), node.fromHubName(),
          node.toHubId(), node.toHubName(),
          node.sequence(),
          node.estimatedDistance(),
          node.estimatedArrivalTime()
      );
    }
  }
}