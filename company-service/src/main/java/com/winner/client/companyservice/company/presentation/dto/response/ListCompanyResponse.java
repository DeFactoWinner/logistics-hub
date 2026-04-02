package com.winner.client.companyservice.company.presentation.dto.response;

import java.util.List;

public record ListCompanyResponse(
    List<CompanyResponse> companies
) {
  public static ListCompanyResponse from(List<CompanyResponse> companies) {
    return new ListCompanyResponse(companies);
  }
}
