package com.winner.client.productservice.product.infrastructure.client.client;

import com.winner.client.global.exception.BusinessException;
import com.winner.client.global.exception.CommonErrorCode;
import com.winner.client.productservice.common.exception.ProductErrorCode;
import com.winner.client.productservice.product.application.service.port.CompanyPort;
import com.winner.client.productservice.product.infrastructure.client.dto.CompanyResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CompanyClientAdapter implements CompanyPort {

  private final CompanyFeignClient companyFeignClient;

  @Override
  public CompanyResponse getCompany(UUID companyId) {

    if (companyId == null) {
      throw new BusinessException(CommonErrorCode.NOT_FOUND);
    }
    try {
      CompanyResponse response = companyFeignClient.getCompany(companyId);
      log.info("CompanyResponse: {}", response);
      log.info("CompanyId: {}", response.getCompanyId());
      log.info("HubID : {}",response.getHubId());

      return response;
    } catch (feign.FeignException.NotFound e) {
      throw new BusinessException(ProductErrorCode.COMPANY_NOT_FOUND);
    }
  }
}
