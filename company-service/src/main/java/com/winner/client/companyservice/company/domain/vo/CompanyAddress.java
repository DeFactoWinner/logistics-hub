package com.winner.client.companyservice.company.domain.vo;

import com.winner.client.global.exception.BusinessException;
import com.winner.client.companyservice.common.exception.CompanyErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompanyAddress {

  @Column(name = "address", nullable = false)
  private String address;

  @Column(name="address_detail")
  private String addressDetail;

  private CompanyAddress(String address, String addressDetail) {
    this.address = address;
    this.addressDetail = addressDetail;
  }

  public static CompanyAddress of(String address, String addressDetail){
    if(address == null || address.isBlank()){
      throw new BusinessException(CompanyErrorCode.ADDRESS_REQUIRED);
    }
    return new CompanyAddress(address,addressDetail);
  }

}
