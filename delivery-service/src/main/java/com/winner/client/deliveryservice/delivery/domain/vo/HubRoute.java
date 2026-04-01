package com.winner.client.deliveryservice.delivery.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HubRoute {
  @Column(name = "origin_id", nullable = false)
  private UUID originHubId;

  @Column(name = "destination_id", nullable = false)
  private UUID destinationHubId;

  public HubRoute(UUID originHubId, UUID destinationHubId) {
    this.originHubId = originHubId;
    this.destinationHubId = destinationHubId;
  }

  public boolean isRelatedTo(UUID hubId) {
    if (hubId == null) return false;
    return originHubId.equals(hubId) || destinationHubId.equals(hubId);
  }
}
