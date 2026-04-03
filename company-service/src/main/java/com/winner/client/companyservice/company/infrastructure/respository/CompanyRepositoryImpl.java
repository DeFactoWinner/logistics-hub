package com.winner.client.companyservice.company.infrastructure.respository;

import com.winner.client.companyservice.company.domain.entity.Company;
import com.winner.client.companyservice.company.domain.repository.CompanyRepository;
import com.winner.client.companyservice.company.domain.vo.CompanyAddress;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CompanyRepositoryImpl implements CompanyRepository {

  private final CompanyJpaRepository jpaRepository;

  @Override
  public Company save(Company company) {
    return jpaRepository.save(company);
  }

  @Override
  public Optional<Company> findById(UUID id) {

    return jpaRepository.findById(id);
  }

  @Override
  public List<Company> findByCompanyName(String name) {

    return jpaRepository.findByCompanyName(name);
  }

  @Override
  public List<Company> findAll() {
    return jpaRepository.findAll();
  }

  @Override
  public Optional<Company> findByIdAndCompanyName(UUID companyId, String companyName) {
    return jpaRepository.findByIdAndCompanyName(companyId,companyName);
  }

  @Override
  public boolean existsByCompanyNameAndAddress(String companyName, CompanyAddress address) {
    return jpaRepository.existsByCompanyNameAndAddress(companyName,address);
  }

}

