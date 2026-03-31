package com.winner.client.companyservice.application;

import com.winner.client.companyservice.application.dto.CompanyServiceDto;
import com.winner.client.companyservice.domain.entity.Company;
import com.winner.client.companyservice.domain.vo.CompanyLocation;
import com.winner.client.companyservice.domain.vo.HubId;
import com.winner.client.companyservice.infrastructure.api.KaKaoAddress;
import com.winner.client.companyservice.presentation.dto.CompanyResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService {

  private final KaKaoAddress kakaoAddress;

  public CompanyResponseDto createCompany(CompanyServiceDto.create serviceDto) {

    HubId hubId = HubId.of(serviceDto.getHubId());

    Company company = Company.create(serviceDto.getName(), serviceDto.getType(),hubId,location);


  }
}
