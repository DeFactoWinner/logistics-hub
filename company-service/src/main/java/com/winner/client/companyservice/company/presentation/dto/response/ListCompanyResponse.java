package com.winner.client.companyservice.company.presentation.dto.response;

import java.util.List;

public record ListCompanyResponse(
    List<CompanyResponse> companies,
    long totalCount
) {
  public static ListCompanyResponse of(List<CompanyResponse> companies, Long totalCount) {
    return new ListCompanyResponse(companies, totalCount);
  }
}
