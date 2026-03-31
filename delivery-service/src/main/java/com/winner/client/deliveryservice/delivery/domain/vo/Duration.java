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
public class Duration {
  private int minutes;

  public Duration(int minutes) {
    if (minutes <= 0) {
      throw new IllegalArgumentException("소요 시간은 0보다 커야 합니다.");
    }
    this.minutes = minutes;
  }
}