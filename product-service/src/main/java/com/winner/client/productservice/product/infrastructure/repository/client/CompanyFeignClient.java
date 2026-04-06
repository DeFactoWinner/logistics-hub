package com.winner.client.productservice.product.infrastructure.repository.client;

import com.winner.client.global.config.FeignConfig;
import com.winner.client.productservice.product.infrastructure.repository.client.dto.CompanyResponse;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "company-service", configuration = FeignConfig.class)
public interface CompanyFeignClient {

  @GetMapping("/api/v1/companies/{companyId}")
  CompanyResponse getCompany(@PathVariable("companyId") UUID companyId);
}
