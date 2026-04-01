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
    validateDifferentHubs(originHubId, destinationHubId);
    this.originHubId = originHubId;
    this.destinationHubId = destinationHubId;
  }

  private static void validateDifferentHubs(UUID originHubId, UUID destinationHubId) {
    if (originHubId.equals(destinationHubId)) {
      throw new IllegalArgumentException("출발 허브와 도착 허브는 동일할 수 없습니다.");
    }
  }
}
