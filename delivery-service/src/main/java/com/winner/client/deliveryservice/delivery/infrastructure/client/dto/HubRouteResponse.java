package com.winner.client.deliveryservice.delivery.infrastructure.client.dto;

import java.util.List;
import java.util.UUID;

public record HubRouteResponse(List<HubNodeInfo> nodes, int count, double totalTime) {
  public record HubNodeInfo(
      UUID fromHubId, String fromHubName,
      UUID toHubId, String toHubName,
      int sequence, double estimatedDistance, double estimatedArrivalTime) {
  }
}