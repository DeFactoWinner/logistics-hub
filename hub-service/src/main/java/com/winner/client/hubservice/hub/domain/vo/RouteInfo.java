package com.winner.client.hubservice.hub.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.UUID;
import lombok.Getter;

@Getter
@Embeddable
public class RouteInfo {

    @Column(name = "from_hub_id", nullable = false)
    private UUID fromHubId;

    @Column(name = "to_hub_id", nullable = false)
    private UUID toHubId;

    protected RouteInfo() {}

    public RouteInfo(UUID fromHubId, UUID toHubId) {
        if (fromHubId == null || toHubId == null) {
            throw new IllegalArgumentException("허브 ID는 null일 수 없습니다.");
        }
        if (fromHubId.equals(toHubId)) {
            throw new IllegalArgumentException("출발 허브와 도착 허브는 같을 수 없습니다.");
        }
        this.fromHubId = fromHubId;
        this.toHubId = toHubId;
    }
}
