package com.winner.client.companyservice.company.presentation.dto.response;

import com.winner.client.companyservice.company.domain.entity.Company;
import com.winner.client.companyservice.company.domain.vo.Type;
import java.util.UUID;

public record CompanyResponse(
    String name,
    String address,
    String addressDetail,
    Type type,
    UUID hubId
) {

  public static CompanyResponse from(Company company){
    return new CompanyResponse(
        company.getCompanyName(),
        company.getAddress().getAddress(),
        company.getAddress().getAddressDetail(),
        company.getType(),
        company.getHubId().getHubId()
        );
  }
}
