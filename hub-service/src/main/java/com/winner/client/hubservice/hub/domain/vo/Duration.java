package com.winner.client.hubservice.hub.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class Duration {

    @Column(name = "route_time", nullable = false)
    private int minutes;

    protected Duration() {}

    public Duration(int minutes) {
        if (minutes <= 0) {
            throw new IllegalArgumentException("소요 시간은 0보다 커야 합니다.");
        }
        this.minutes = minutes;
    }
}
