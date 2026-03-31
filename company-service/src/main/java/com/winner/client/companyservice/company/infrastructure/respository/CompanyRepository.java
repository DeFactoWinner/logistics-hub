package com.winner.client.companyservice.company.infrastructure.respository;

import com.winner.client.companyservice.company.domain.entity.Company;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, UUID> {


}
