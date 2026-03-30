package com.winner.client.deliveryservice.delivery.domain.vo;

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
    validateNotEmpty(roadAddress, "도로명 주소");
    validateNotEmpty(detailAddress, "상세 주소");
    this.roadAddress = roadAddress;
    this.detailAddress = detailAddress;
  }

  private void validateNotEmpty(String value, String fieldName) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException(fieldName + "는 비워둘 수 없습니다.");
    }
  }
}
