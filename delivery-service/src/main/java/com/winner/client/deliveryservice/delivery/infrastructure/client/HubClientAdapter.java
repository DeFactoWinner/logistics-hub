package com.winner.client.deliveryservice.delivery.infrastructure.client;

import com.winner.client.deliveryservice.delivery.application.dto.external.HubRouteInfo;
import com.winner.client.deliveryservice.delivery.application.reader.HubRouteReader;
import com.winner.client.deliveryservice.delivery.infrastructure.client.dto.HubRouteResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HubClientAdapter implements HubRouteReader {

  private final HubClient hubClient;

  @Override
  public HubRouteInfo getHubRoutes(UUID fromHubId, UUID toHubId) {
    HubRouteResponse response = hubClient.getHubRoutes(fromHubId, toHubId);
    return HubRouteInfo.from(response);
  }
}