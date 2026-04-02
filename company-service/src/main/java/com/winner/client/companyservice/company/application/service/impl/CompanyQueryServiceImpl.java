package com.winner.client.companyservice.company.application.service.impl;

import com.winner.client.companyservice.common.exception.BusinessException;
import com.winner.client.companyservice.common.exception.CompanyErrorCode;
import com.winner.client.companyservice.company.application.service.CompanyQueryService;
import com.winner.client.companyservice.company.domain.repository.CompanyRepository;
import com.winner.client.companyservice.company.presentation.dto.response.CompanyResponse;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CompanyQueryServiceImpl implements CompanyQueryService {

  private final CompanyRepository companyRepository;

  @Override
  public List<CompanyResponse> getCompanyList() {
    return companyRepository.findAll().stream()
        .map(CompanyResponse::from)
        .toList();
  }

  @Override
  public CompanyResponse getCompany(UUID companyId) {
    return companyRepository.findById(companyId).
        map(CompanyResponse::from).orElseThrow(()->
            new BusinessException(CompanyErrorCode.COMPANY_NOT_FOUND));
  }

  @Override
  public CompanyResponse getCompanyByIdAndCompanyName(UUID companyId, String companyName) {
    return companyRepository.findByIdAndCompanyName(companyId,companyName).
        map(CompanyResponse::from).orElseThrow(() ->
        new BusinessException(CompanyErrorCode.COMPANY_NOT_FOUND));
  }

  @Override
  public List<CompanyResponse> getCompanyListByCompanyName(String companyName) {
    return companyRepository.findByCompanyName(companyName).stream()
            .map(CompanyResponse::from)
            .toList();
  }

}
