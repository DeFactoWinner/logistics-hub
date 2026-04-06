package com.winner.client.companyservice.company.application.service;

import com.winner.client.companyservice.company.presentation.dto.request.CreateCompanyRequest;
import com.winner.client.companyservice.company.presentation.dto.request.UpdateCompanyRequest;
import com.winner.client.companyservice.company.presentation.dto.response.CompanyResponse;
import com.winner.client.global.security.CustomUserPrincipal;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CompanyCommandService {

  CompanyResponse createCompany(CreateCompanyRequest request, CustomUserPrincipal principal);

  CompanyResponse updateCompany(UUID id,UpdateCompanyRequest request, CustomUserPrincipal principal);

  void deleteCompany(UUID companyId, CustomUserPrincipal principal);

}
