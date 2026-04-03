package com.winner.client.hubservice.hub.domain.entity;

import com.winner.client.hubservice.common.exception.hub.HubErrorCode;
import com.winner.client.hubservice.common.exception.hub.HubException;
import com.winner.client.hubservice.hub.domain.vo.HubLocation;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.util.UUID;
import lombok.Getter;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Getter
public class Hub {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Embedded
    private HubLocation location;

    protected Hub() {}

    private Hub(String name, HubLocation location) {
        if (name == null || name.isBlank()) {
            throw new HubException(HubErrorCode.INVALID_HUB_NAME);
        }
        this.name = name;
        this.location = location;
    }

    public static Hub create(String name, HubLocation location) {
        return new Hub(name, location);
    }
}
