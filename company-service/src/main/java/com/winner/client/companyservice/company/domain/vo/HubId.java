package com.winner.client.companyservice.company.domain.vo;

import com.winner.client.companyservice.common.exception.BusinessException;
import com.winner.client.companyservice.common.exception.CompanyErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class HubId {

  @Column(name = "hub_id", nullable = false)
  private UUID hubId;

  public static HubId of(UUID hubId) {
    if (hubId == null) {
      throw new BusinessException(CompanyErrorCode.HUD_ID_REQUIRED);
    }
      return new HubId(hubId);
  }


}
