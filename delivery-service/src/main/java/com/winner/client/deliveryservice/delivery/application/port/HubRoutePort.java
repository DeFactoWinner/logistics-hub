package com.winner.client.deliveryservice.delivery.application.port;

import com.winner.client.deliveryservice.delivery.application.dto.external.HubRouteInfo;
import java.util.UUID;

public interface HubRoutePort {
  HubRouteInfo getHubRoutes(UUID fromHubId, UUID toHubId);
}
