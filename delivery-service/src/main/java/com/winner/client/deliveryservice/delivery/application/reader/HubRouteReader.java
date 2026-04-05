package com.winner.client.deliveryservice.delivery.application.reader;

import com.winner.client.deliveryservice.delivery.application.dto.external.HubRouteInfo;
import java.util.UUID;

public interface HubRouteReader {
  HubRouteInfo getHubRoutes(UUID fromHubId, UUID toHubId);
}
