package com.winner.client.companyservice.company.entity.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompanyLocation {

  @Column(name = "lat", nullable = false)
  private Double latitude;

  @Column(name = "lng", nullable = false)
  private Double longitude;

  public static CompanyLocation of(Double lng, Double lat) {

    if (lat < -90 || lat > 90) {
      throw new IllegalArgumentException("유효하지 않은 위도 좌표입니다.");
    }
    if(lng < -180 || lng > 180 ){
      throw new IllegalArgumentException("유효하지 않은 경도 좌표입니다.");
    }
    return new CompanyLocation(lat, lng);
  }

}
