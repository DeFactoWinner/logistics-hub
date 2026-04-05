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
public class Location {
  private double latitude;
  private double longitude;

  public Location(double latitude, double longitude) {
    validateLatitude(latitude);
    validateLongitude(longitude);
    this.latitude = latitude;
    this.longitude = longitude;
  }

  private void validateLatitude(double latitude) {
    if (latitude < -90 || latitude > 90) {
      throw new BusinessException(DeliveryErrorCode.INVALID_LATITUDE);
    }
  }

  private void validateLongitude(double longitude) {
    if (longitude < -180 || longitude > 180) {
      throw new BusinessException(DeliveryErrorCode.INVALID_LONGITUDE);
    }
  }
}
