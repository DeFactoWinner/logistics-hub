package com.winner.client.companyservice.company.application.service.impl;

import com.winner.client.companyservice.common.exception.CompanyErrorCode;
import com.winner.client.companyservice.company.application.service.CompanyCommandService;
import com.winner.client.companyservice.company.application.service.port.HubPort;
import com.winner.client.companyservice.company.application.service.port.UserPort;
import com.winner.client.companyservice.company.application.service.validate.CompanyValidator;
import com.winner.client.companyservice.company.domain.entity.Company;
import com.winner.client.companyservice.company.domain.repository.CompanyRepository;
import com.winner.client.companyservice.company.domain.vo.CompanyAddress;
import com.winner.client.companyservice.company.domain.vo.CompanyLocation;
import com.winner.client.companyservice.company.domain.vo.HubId;
import com.winner.client.companyservice.company.infrastructure.client.dto.response.HubResponse;
import com.winner.client.companyservice.company.infrastructure.service.GeocodingService;
import com.winner.client.companyservice.company.presentation.dto.request.CreateCompanyRequest;
import com.winner.client.companyservice.company.presentation.dto.request.UpdateCompanyRequest;
import com.winner.client.companyservice.company.presentation.dto.response.CompanyResponse;
import com.winner.client.global.exception.BusinessException;
import com.winner.client.global.security.CustomUserPrincipal;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompanyCommandImpl implements CompanyCommandService {

  private final GeocodingService geocodingService;
  private final CompanyRepository companyRepository;
  private final CompanyValidator companyValidate;
  private final HubPort hubPort;
  private final UserPort userPort;

  @Override
  public CompanyResponse createCompany(CreateCompanyRequest request, CustomUserPrincipal userPrincipal) {

    CompanyLocation companyLocation = convert(request.address());
    CompanyAddress address = CompanyAddress.of(request.address(), request.addressDetail());

    HubResponse hubResponse = hubPort.getHub(request.hubId());

    companyValidate.validateCreate(userPrincipal,hubResponse.id());

    HubId hubId = HubId.of(hubResponse.id());

    Company company = Company.create(request.name(), request.type(), hubId, companyLocation, address);

    companyRepository.save(company);

    return CompanyResponse.from(company);
  }

  @Override
  public CompanyResponse updateCompany(UUID companyId, UpdateCompanyRequest request, CustomUserPrincipal userPrincipal) {
    Company company = companyRepository.findById(companyId).orElseThrow(() ->
        new BusinessException(CompanyErrorCode.COMPANY_NOT_FOUND));

    CompanyLocation location = request.address() == null ? null : convert(request.address());
    CompanyAddress address = request.address() == null ? null :
        CompanyAddress.of(request.address(), request.addressDetail());

    HubId hubId = null;

    companyValidate.validateCreate(userPrincipal, company.getHubId().getHubId());

    if(request.hubId() != null){
      HubResponse hubResponse = hubPort.getHub(request.hubId());
      hubId = HubId.of(hubResponse.id());
    }

    companyValidate.validateAccess(company,userPrincipal,"update");

    company.updateCompany(request.name(), hubId, location, address);

    return CompanyResponse.from(company);

  }

  @Override
  public void deleteCompany(UUID id, CustomUserPrincipal userPrincipal) {
    Company company = companyRepository.findById(id).orElseThrow(()->
       new BusinessException(CompanyErrorCode.COMPANY_NOT_FOUND));

    companyValidate.validateAccess(company,userPrincipal,"delete");

    userPort.unassignUsersByCompany(id);

    company.softDelete(userPrincipal.userId());
  }

  private CompanyLocation convert(String address) {

    Double[] location = geocodingService.convert(address);

    return CompanyLocation.of(location[0], location[1]);
  }

}
