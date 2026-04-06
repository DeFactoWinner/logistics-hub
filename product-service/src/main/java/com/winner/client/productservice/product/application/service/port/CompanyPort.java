package com.winner.client.productservice.product.application.service.port;

import com.winner.client.productservice.product.infrastructure.repository.client.dto.CompanyResponse;
import java.util.UUID;

public interface CompanyPort {

  CompanyResponse getCompany(UUID companyId);

}
