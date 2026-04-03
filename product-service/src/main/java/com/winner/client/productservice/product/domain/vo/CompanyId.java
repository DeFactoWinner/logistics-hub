package com.winner.client.productservice.product.domain.vo;

import com.winner.client.global.exception.BusinessException;
import com.winner.client.productservice.common.exception.ProductErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompanyId {

  @Column(name = "company_id", nullable = false)
  private UUID companyId;

  private CompanyId(UUID companyId) {
    validate(companyId);
    this.companyId = companyId;
  }

  public static CompanyId of(UUID companyId){
    return new CompanyId(companyId);
  }

  private void validate(UUID companyId) {
    if (companyId == null) {
      throw new BusinessException(ProductErrorCode.COMPANY_ID_REQUIRED);
    }
  }

}
