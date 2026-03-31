package com.winner.client.companyservice.company.domain.vo;

import com.winner.client.companyservice.common.exception.BusinessException;
import com.winner.client.companyservice.common.exception.CompanyErrorCode;
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
      throw new BusinessException(CompanyErrorCode.INVALID_LATITUDE);
    }
    if(lng < -180 || lng > 180 ){
      throw new BusinessException(CompanyErrorCode.INVALID_LONGITUDE);
    }
    return new CompanyLocation(lat, lng);
  }

}
