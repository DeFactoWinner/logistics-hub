package com.winner.client.deliveryservice.delivery.domain.vo;

import com.winner.client.deliveryservice.common.exception.delivery.DeliveryErrorCode;
import com.winner.client.global.exception.BusinessException;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {
  private String roadAddress;
  private String detailAddress;

  public Address(String roadAddress, String detailAddress) {
    validateNotEmpty(roadAddress);
    validateNotEmpty(detailAddress);
    this.roadAddress = roadAddress;
    this.detailAddress = detailAddress;
  }

  private void validateNotEmpty(String value) {
    if (value == null || value.trim().isEmpty()) {
      throw new BusinessException(DeliveryErrorCode.FIELD_CANNOT_BE_EMPTY_ADDRESS);
    }
  }
}
