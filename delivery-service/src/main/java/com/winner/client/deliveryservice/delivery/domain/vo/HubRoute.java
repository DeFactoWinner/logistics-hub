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
  private UUID originId;

  @Column(name = "destination_id", nullable = false)
  private UUID destinationId;

  public HubRoute(UUID originId, UUID destinationId) {
    validateDifferentHubs(originId, destinationId);
    this.originId = originId;
    this.destinationId = destinationId;
  }

  private static void validateDifferentHubs(UUID originId, UUID destinationId) {
    if (originId.equals(destinationId)) {
      throw new IllegalArgumentException("출발 허브와 도착 허브는 동일할 수 없습니다.");
    }
  }
}
