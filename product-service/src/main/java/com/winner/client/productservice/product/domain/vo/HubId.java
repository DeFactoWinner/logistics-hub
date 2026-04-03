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
public class HubId {

  @Column(name = "hub_id", nullable = false)
  private UUID hubId;

  private HubId(UUID hubId) {
    validate(hubId);
    this.hubId = hubId;
  }

  public static HubId of(UUID hubId){
    return new HubId(hubId);
  }

  private void validate(UUID hubId) {
    if (hubId == null) {
      throw new BusinessException(ProductErrorCode.HUB_ID_REQUIRED);
    }
  }

}
