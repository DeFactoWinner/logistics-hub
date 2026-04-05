package com.winner.client.deliveryservice.delivery.domain.vo;

import com.winner.client.deliveryservice.common.exception.delivery.DeliveryErrorCode;
import com.winner.client.global.exception.BusinessException;
import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Distance {

  private BigDecimal kilometers;

  public Distance(BigDecimal kilometers) {
    if (kilometers.compareTo(BigDecimal.ZERO) <= 0) {
      throw new BusinessException(DeliveryErrorCode.INVALID_DISTANCE);
    }
    this.kilometers = kilometers;
  }
}