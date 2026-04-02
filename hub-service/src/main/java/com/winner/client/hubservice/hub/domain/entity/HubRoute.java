package com.winner.client.hubservice.hub.domain.entity;

import com.winner.client.hubservice.hub.domain.vo.Distance;
import com.winner.client.hubservice.hub.domain.vo.Duration;
import com.winner.client.hubservice.hub.domain.vo.RouteInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.UUID;
import lombok.Getter;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Getter
@Table(
    uniqueConstraints = @UniqueConstraint(
        name = "uq_hub_route",
        columnNames = {"from_hub_id", "to_hub_id"}
    )
)
public class HubRoute {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", nullable = false)
    private UUID id;

    @Embedded
    private RouteInfo routeInfo;

    @Embedded
    private Distance distance;

    @Embedded
    private Duration duration;

    protected HubRoute() {}

    private HubRoute(RouteInfo routeInfo, Distance distance, Duration duration) {
        if (routeInfo == null || distance == null || duration == null) {
            throw new IllegalArgumentException("경로 정보는 null일 수 없습니다.");
        }
        this.routeInfo = routeInfo;
        this.distance = distance;
        this.duration = duration;
    }

    public static HubRoute create(RouteInfo routeInfo, Distance distance, Duration duration) {
        return new HubRoute(routeInfo, distance, duration);
    }
}
