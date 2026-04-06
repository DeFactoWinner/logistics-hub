package com.winner.client.companyservice.company.application.service.validate;

import com.winner.client.companyservice.company.domain.entity.Company;
import com.winner.client.global.exception.BusinessException;
import com.winner.client.global.exception.CommonErrorCode;
import com.winner.client.global.security.CustomUserPrincipal;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class CompanyValidator {

  public void validateCreate(CustomUserPrincipal user, UUID requestedHubId) {
    if ("MASTER".equals(user.role())) return;

    if ("HUB_MANAGER".equals(user.role())) {
      if (requestedHubId.equals(user.referenceId())) return;
    }
    throw new BusinessException(CommonErrorCode.FORBIDDEN);
  }

  public void validateAccess(Company company, CustomUserPrincipal user, String action) {
    if ("MASTER".equals(user.role())) return;

    if ("HUB_MANAGER".equals(user.role())) {
      if (company.getHubId().getHubId().equals(user.referenceId())) return;
    }

    if ("COMPANY_MANAGER".equals(user.role()) && "UPDATE".equals(action)) {
      if (company.getId().equals(user.referenceId())) return;
    }

    throw new BusinessException(CommonErrorCode.FORBIDDEN);
  }

}
