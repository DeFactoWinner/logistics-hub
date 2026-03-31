package com.winner.client.hubservice.hub.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class Distance {

    @Column(name = "route_distance", nullable = false)
    private double value;

    protected Distance() {}

    public Distance(double value) {
        if (value < 0) {
            throw new IllegalArgumentException("거리는 0 이상이어야 합니다.");
        }
        this.value = value;
    }
}
