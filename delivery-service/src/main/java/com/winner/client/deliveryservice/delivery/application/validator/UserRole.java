package com.winner.client.deliveryservice.delivery.application.validator;

import com.winner.client.deliveryservice.common.exception.delivery.DeliveryErrorCode;
import com.winner.client.global.exception.BusinessException;

public enum UserRole {
  MASTER,
  HUB_MANAGER,
  COMPANY_MANAGER,
  DELIVERY_MANAGER;

  public static UserRole of(String roleName) {
    try {
      return UserRole.valueOf(roleName.toUpperCase());
    } catch (IllegalArgumentException | NullPointerException e) {
      throw new BusinessException(DeliveryErrorCode.INVALID_ROLE);
    }
  }
}
