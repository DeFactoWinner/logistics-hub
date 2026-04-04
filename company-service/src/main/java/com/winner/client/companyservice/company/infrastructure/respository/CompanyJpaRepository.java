package com.winner.client.companyservice.company.infrastructure.respository;

import com.winner.client.companyservice.company.domain.entity.Company;
import com.winner.client.companyservice.company.domain.vo.CompanyAddress;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyJpaRepository extends JpaRepository<Company, UUID>{

  List<Company> findByCompanyName(String name);

  boolean existsByCompanyNameAndAddress(String companyName, CompanyAddress address);

  Optional<Company> findByIdAndCompanyName(UUID companyId, String companyName);
}
