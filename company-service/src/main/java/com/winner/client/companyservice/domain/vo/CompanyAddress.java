package com.winner.client.companyservice.domain.vo;

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
public class CompanyAddress {

  @Column(name = "address", nullable = false)
  private String address;

  @Column(name="address_detail")
  private String addressDetail;

  public static CompanyAddress of(String address, String addressDetail){
    if(address == null || address.isBlank()){
      throw new IllegalArgumentException("주소는 필수 값입니다.");
    }
    return new CompanyAddress(address,addressDetail);
  }

}
