package com.winner.client.hubservice.hub.domain.vo;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Getter;

@Getter
@Embeddable
public class RouteInfo {

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "from_hub_id", nullable = false))
    private HubId fromHubId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "to_hub_id", nullable = false))
    private HubId toHubId;

    protected RouteInfo() {}

    public RouteInfo(HubId fromHubId, HubId toHubId) {
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
