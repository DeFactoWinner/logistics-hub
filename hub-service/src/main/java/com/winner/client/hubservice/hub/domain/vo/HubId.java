package com.winner.client.hubservice.hub.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;

@Getter
@Embeddable
public class HubId {

    @Column(name = "hub_id")
    private UUID value;

    protected HubId() {}

    public HubId(UUID value) {
        if (value == null) {
            throw new IllegalArgumentException("HubId는 null일 수 없습니다.");
        }
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HubId)) return false;
        HubId hubId = (HubId) o;
        return Objects.equals(value, hubId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
