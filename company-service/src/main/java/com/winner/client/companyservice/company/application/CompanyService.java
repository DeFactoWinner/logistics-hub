package com.winner.client.companyservice.company.application;

import com.winner.client.companyservice.company.application.dto.CompanyServiceDto;
import com.winner.client.companyservice.company.domain.entity.Company;
import com.winner.client.companyservice.company.domain.vo.CompanyAddress;
import com.winner.client.companyservice.company.domain.vo.CompanyLocation;
import com.winner.client.companyservice.company.domain.vo.HubId;
import com.winner.client.companyservice.company.infrastructure.api.KaKaoAddress;
import com.winner.client.companyservice.company.infrastructure.respository.CompanyRepository;
import com.winner.client.companyservice.company.presentation.dto.CompanyResponseDto;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class CompanyService {

  private final KaKaoAddress kakaoAddress;
  private final CompanyRepository companyRepository;

  @Transactional
  public CompanyResponseDto createCompany(CompanyServiceDto.create serviceDto) {

    CompanyLocation companyLocation = convert(serviceDto.getAddress());
    CompanyAddress address = CompanyAddress.of(serviceDto.getAddress(), serviceDto.getAddressDetail());
    HubId hubId = HubId.of(serviceDto.getHubId());

    Company company = Company.create(serviceDto.getName(),serviceDto.getType(), hubId, companyLocation, address);

    companyRepository.save(company);

    return CompanyResponseDto.from(company);

  }

  @Transactional
  public CompanyResponseDto updateCompany(UUID companyId, CompanyServiceDto.update serviceDto) {

    Company company = companyRepository.findById(companyId).orElseThrow();

    CompanyLocation location = serviceDto.getAddress() == null ? null : convert(serviceDto.getAddress());
    CompanyAddress address = serviceDto.getAddress() == null ? null :
        CompanyAddress.of(serviceDto.getAddress(), serviceDto.getAddressDetail());

    HubId hubId = serviceDto.getHubId() == null ? null : HubId.of(serviceDto.getHubId());

    company.updateCompany(serviceDto.getName(), hubId, location, address);

    return CompanyResponseDto.from(company);
  }
  
  public List<CompanyResponseDto> selectCompanyList() {
    
    List<Company> companyList = companyRepository.findAll();
    
    return companyList.stream().map(CompanyResponseDto::from).toList();
  }
  
  public CompanyResponseDto selectCompany(UUID companyId) {
    
    return CompanyResponseDto.from(companyRepository.findById(companyId).orElseThrow());
  }

  @Transactional
  public void deleteCompany(UUID companyId) {
    Company company = companyRepository.findById(companyId).orElseThrow();

    company.softDelete(UUID.randomUUID());
  }

  private CompanyLocation convert(String address) {

    Double[] location = kakaoAddress.convert(address);

    return CompanyLocation.of(location[0], location[1]);
  }
}
