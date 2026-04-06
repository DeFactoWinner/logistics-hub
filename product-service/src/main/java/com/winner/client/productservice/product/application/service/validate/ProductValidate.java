package com.winner.client.productservice.product.application.service.validate;

import com.winner.client.global.exception.BusinessException;
import com.winner.client.global.exception.CommonErrorCode;
import com.winner.client.global.security.CustomUserPrincipal;
import com.winner.client.productservice.product.infrastructure.repository.client.dto.CompanyResponse;
import org.springframework.stereotype.Component;

@Component
public class ProductValidate {

  public void upsertProduct(CompanyResponse response,
      CustomUserPrincipal userPrincipal) {

    if("MASTER".equals(userPrincipal.role())) return;

    if("HUB_MANAGER".equals(userPrincipal.role())){
      if(userPrincipal.referenceId().equals(response.hubId())) return;
    }

    if("COMPANY_MANAGER".equals(userPrincipal.role())){
      if(userPrincipal.referenceId().equals(response.companyId())) return;
    }

    throw new BusinessException(CommonErrorCode.FORBIDDEN);
  }

  public void deleteProduct(CompanyResponse response,
      CustomUserPrincipal userPrincipal) {

    if("MASTER".equals(userPrincipal.role())) return;

    if("HUB_MANAGER".equals(userPrincipal.role())){
      if(userPrincipal.referenceId().equals(response.hubId())) return;
    }
    throw new BusinessException(CommonErrorCode.FORBIDDEN);
  }
}
