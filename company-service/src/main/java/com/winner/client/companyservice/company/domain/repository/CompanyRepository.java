package com.winner.client.companyservice.company.domain.repository;

import com.winner.client.companyservice.company.domain.entity.Company;
import com.winner.client.companyservice.company.domain.vo.CompanyAddress;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository {

  Company save(Company company);

  Optional<Company> findById(UUID id);

  List<Company> findByCompanyName(String name);

  List<Company> findAll();

  Optional<Company> findByIdAndCompanyName(UUID companyId, String companyName);

  boolean existsByCompanyNameAndAddress(String companyName, CompanyAddress address);
}