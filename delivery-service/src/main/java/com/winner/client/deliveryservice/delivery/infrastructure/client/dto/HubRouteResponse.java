package com.winner.client.deliveryservice.delivery.infrastructure.client.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record HubRouteResponse(List<HubNodeInfo> nodes, int count) {
  public record HubNodeInfo(
      UUID fromHubId, String fromHubName,
      UUID toHubId, String toHubName,
      int sequence, BigDecimal estimatedDistance, int estimatedArrivalTime) {
  }
}
