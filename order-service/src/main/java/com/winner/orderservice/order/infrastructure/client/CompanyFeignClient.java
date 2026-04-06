package com.winner.orderservice.order.infrastructure.client;

import com.winner.client.global.response.ApiResponse;
import com.winner.orderservice.order.infrastructure.client.dto.response.CompanyResponse;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "company-service", url = "${feign.company-service.url:}")
public interface CompanyFeignClient {

  @GetMapping("/api/v1/companies/{companyId}")
  ApiResponse<CompanyResponse> getCompany(@PathVariable("companyId") UUID companyId);
}
