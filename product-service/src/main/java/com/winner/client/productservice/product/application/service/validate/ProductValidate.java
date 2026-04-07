package com.winner.client.productservice.product.application.service.validate;

import com.winner.client.global.exception.BusinessException;
import com.winner.client.global.exception.CommonErrorCode;
import com.winner.client.global.security.CustomUserPrincipal;
import com.winner.client.productservice.product.domain.vo.CompanyId;
import com.winner.client.productservice.product.domain.vo.HubId;
import com.winner.client.productservice.product.infrastructure.client.dto.CompanyResponse;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class ProductValidate {

  public void createProduct(CompanyResponse response,
      CustomUserPrincipal userPrincipal) {

    if("MASTER".equals(userPrincipal.role())) return;

    if("HUB_MANAGER".equals(userPrincipal.role())){
      if(userPrincipal.referenceId().equals(response.getHubId())) return;
    }

    if("COMPANY_MANAGER".equals(userPrincipal.role())){
      if(userPrincipal.referenceId().equals(response.getCompanyId())) return;
    }

    throw new BusinessException(CommonErrorCode.FORBIDDEN);
  }

  public void updateProduct(CompanyId companyId, HubId hubId, CustomUserPrincipal userPrincipal) {
    if("MASTER".equals(userPrincipal.role())) return;

    if("HUB_MANAGER".equals(userPrincipal.role())){
      if(userPrincipal.referenceId().equals(hubId.getHubId())) return;
    }

    if("COMPANY_MANAGER".equals(userPrincipal.role())){
      if(userPrincipal.referenceId().equals(companyId.getCompanyId())) return;
    }

    throw new BusinessException(CommonErrorCode.FORBIDDEN);
  }

  public void deleteProduct(CompanyResponse response,
      CustomUserPrincipal userPrincipal) {

    if("MASTER".equals(userPrincipal.role())) return;

    if("HUB_MANAGER".equals(userPrincipal.role())){
      if(userPrincipal.referenceId().equals(response.getHubId())) return;
    }
    throw new BusinessException(CommonErrorCode.FORBIDDEN);
  }

  public void searchAndReadProduct(UUID hubId,
      CustomUserPrincipal userPrincipal) {

    if("HUB_MANAGER".equals(userPrincipal.role())){
      if(userPrincipal.referenceId().equals(hubId)) return;
    }

  }

}
