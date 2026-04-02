package com.winner.client.productservice.product.domain.vo;

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
  private void validate(UUID companyId) {
    if (companyId == null) {
      throw new IllegalArgumentException("id는 필수값입니다.");
    }
  }

}
