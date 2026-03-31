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
public class Distance {
  private int kilometers;

  public Distance(int kilometers) {
    if (kilometers <= 0) {
      throw new IllegalArgumentException("거리는 0보다 커야 합니다.");
    }
    this.kilometers = kilometers;
  }
}