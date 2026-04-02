package com.winner.client.companyservice.company.application.service;

import com.winner.client.companyservice.company.presentation.dto.response.CompanyResponse;
import java.util.List;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface CompanyQueryService {

  List<CompanyResponse> getCompanyList();

  CompanyResponse getCompany(UUID companyId);

  CompanyResponse getCompanyByIdAndCompanyName(UUID companyId, String companyName);

  List<CompanyResponse> getCompanyListByCompanyName(String companyName);

}
