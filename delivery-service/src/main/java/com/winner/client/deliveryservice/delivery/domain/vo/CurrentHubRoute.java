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
public class CurrentHubRoute {
  @Column(name = "cur_hub_id", nullable = false)
  private UUID curHubId;

  @Column(name = "next_hub_id", nullable = false)
  private UUID nextHubId;

  public CurrentHubRoute(UUID curHubId, UUID nextHubId) {
    validateDifferentHubs(curHubId, nextHubId);
    this.curHubId = curHubId;
    this.nextHubId = nextHubId;
  }

  private static void validateDifferentHubs(UUID curHubId, UUID nextHubId) {
    if (curHubId.equals(nextHubId)) {
      throw new IllegalArgumentException("현재 허브와 다음 허브는 동일할 수 없습니다.");
    }
  }

  public boolean isRelatedTo(UUID hubId) {
    if (hubId == null) return false;
    return curHubId.equals(hubId) || nextHubId.equals(hubId);
  }
}
