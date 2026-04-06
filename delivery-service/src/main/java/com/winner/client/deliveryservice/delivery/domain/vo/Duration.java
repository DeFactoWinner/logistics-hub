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
public class Duration {
  private double minutes;

  public Duration(double minutes) {
    if (minutes <= 0) {
      throw new BusinessException(DeliveryErrorCode.INVALID_DURATION);
    }
    this.minutes = minutes;
  }
}