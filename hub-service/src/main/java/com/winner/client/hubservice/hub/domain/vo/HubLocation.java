package com.winner.client.hubservice.hub.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class HubLocation {

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "lat", nullable = false)
    private double lat;

    @Column(name = "lng", nullable = false)
    private double lng;

    protected HubLocation() {}

    public HubLocation(String address, double lat, double lng) {
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("주소는 null이거나 빈 문자열일 수 없습니다.");
        }
        if (lat < -90 || lat > 90) {
            throw new IllegalArgumentException("위도는 -90에서 90 사이여야 합니다.");
        }
        if (lng < -180 || lng > 180) {
            throw new IllegalArgumentException("경도는 -180에서 180 사이여야 합니다.");
        }

        this.address = address;
        this.lat = lat;
        this.lng = lng;
    }
}
