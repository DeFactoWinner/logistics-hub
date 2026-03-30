package com.winner.client.companyservice.domain.vo;

import com.winner.client.companyservice.infrastructure.api.KaKaoAddress;
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

  @Column(name = "address", nullable = false)
  private String address;

  @Column(name = "lat", nullable = false)
  private Double latitude;

  @Column(name = "lng", nullable = false)
  private Double longitude;

  public static CompanyLocation of(String address, KaKaoAddress kaKaoAddress) {

    Double[] result = kaKaoAddress.convert(address);
    if(result == null || result.length < 2){
      throw new IllegalArgumentException("위경도를 찾을 수 없습니다.");
    }else{
      return new CompanyLocation(address, result[0], result[1]);
    }
  }

}
