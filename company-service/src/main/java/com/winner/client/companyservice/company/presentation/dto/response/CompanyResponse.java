package com.winner.client.companyservice.company.presentation.dto.response;

import com.winner.client.companyservice.company.domain.entity.Company;
import java.util.UUID;

public record CompanyResponse(
    UUID companyId,
    String name,
    String address,
    String addressDetail,
    String type,
    UUID hubId
) {

  public static CompanyResponse from(Company company){
    return new CompanyResponse(
        company.getId(),
        company.getCompanyName(),
        company.getAddress().getAddress(),
        company.getAddress().getAddressDetail(),
        company.getType().name(),
        company.getHubId().getHubId()
        );
  }
}
