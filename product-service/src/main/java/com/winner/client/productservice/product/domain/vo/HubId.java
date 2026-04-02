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
public class HubId {

  @Column(name = "hub_id", nullable = false)
  private UUID hubId;

  private HubId(UUID hubId) {
    validate(hubId);
    this.hubId = hubId;
  }
  private void validate(UUID hubId) {
    if (hubId == null) {
      throw new IllegalArgumentException("id는 필수값입니다.");
    }
  }

}
