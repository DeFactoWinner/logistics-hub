package com.winner.client.companyservice.company.presentation.dto;

import com.winner.client.companyservice.company.domain.entity.Company;
import com.winner.client.companyservice.company.domain.vo.Type;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CompanyResponseDto {

  private String name;
  private String address;
  private String addressDetail;
  private Type type;
  private UUID hubId;

  public static CompanyResponseDto from(Company company){
    return CompanyResponseDto.builder()
        .name(company.getCompanyName())
        .address(company.getAddress().getAddress())
        .addressDetail(company.getAddress().getAddressDetail())
        .type(company.getType())
        .hubId(company.getHubId().getHubId())
        .build();
  }
}
