package com.winner.client.companyservice.company.application.service;

import com.winner.client.companyservice.company.presentation.dto.request.CreateCompanyRequest;
import com.winner.client.companyservice.company.presentation.dto.request.UpdateCompanyRequest;
import com.winner.client.companyservice.company.presentation.dto.response.CompanyResponse;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CompanyCommandService {

  CompanyResponse createCompany(CreateCompanyRequest request);

  CompanyResponse updateCompany(UUID id,UpdateCompanyRequest request);

  void deleteCompany(UUID companyId);

}
