package com.winner.client.companyservice.company.domain.vo;

import com.winner.client.global.exception.BusinessException;
import com.winner.client.companyservice.common.exception.CompanyErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HubId {

  @Column(name = "hub_id", nullable = false)
  private UUID hubId;

  private HubId(UUID hubId) {
    this.hubId = hubId;
  }

  public static HubId of(UUID hubId) {
    if (hubId == null) {
      throw new BusinessException(CompanyErrorCode.HUB_ID_REQUIRED);
    }
      return new HubId(hubId);
  }


}
