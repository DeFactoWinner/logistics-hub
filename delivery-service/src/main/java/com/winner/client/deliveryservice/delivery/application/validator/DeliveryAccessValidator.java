package com.winner.client.deliveryservice.delivery.application.validator;

import com.winner.client.deliveryservice.common.exception.delivery.DeliveryErrorCode;
import com.winner.client.deliveryservice.delivery.domain.entity.Delivery;
import com.winner.client.deliveryservice.delivery.domain.entity.DeliveryRoute;
import com.winner.client.global.exception.BusinessException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryAccessValidator {

  public void validateRole(String userRole, UserRole... allowedRoles) {
    UserRole role = UserRole.of(userRole);
    for (UserRole allowed : allowedRoles) {
      if (allowed == role) return;
    }
    throw new BusinessException(DeliveryErrorCode.ACCESS_DENIED_ROLE);
  }

  public void validateDeliveryHubAccess(Delivery delivery, UUID userHubId) {
    if (!delivery.isRelatedToHub(userHubId)) {
      throw new BusinessException(DeliveryErrorCode.ACCESS_DENIED_HUB_ADMIN);
    }
  }

  public void validateRouteHubAccess(DeliveryRoute route, UUID userHubId) {
    if (!route.isRelatedToHub(userHubId)) {
      throw new BusinessException(DeliveryErrorCode.ACCESS_DENIED_HUB_ADMIN);
    }
  }

  public void validateDeliveryManager(Delivery delivery, UUID userId) {
    if (delivery.getDeliveryManagerId() == null || !delivery.getDeliveryManagerId().equals(userId)) {
      throw new BusinessException(DeliveryErrorCode.ACCESS_DENIED_DELIVERY_MANAGER);
    }
  }

  public void validateDeliveryManager(DeliveryRoute route, UUID userId) {
    if (route.getDeliveryManagerId() == null || !route.getDeliveryManagerId().equals(userId)) {
      throw new BusinessException(DeliveryErrorCode.ACCESS_DENIED_DELIVERY_MANAGER);
    }
  }

  public void validateHubAdminAccess(Delivery delivery, String userRole, UUID userHubId) {
    UserRole role = UserRole.of(userRole);
    validateRole(userRole, UserRole.MASTER, UserRole.HUB_MANAGER);

    if (role == UserRole.HUB_MANAGER) {
      validateDeliveryHubAccess(delivery, userHubId);
    }
  }

  public void validateDeliveryManagerAccess(
      Delivery delivery, UUID userId, String userRole, UUID userHubId
  ) {
    UserRole role = UserRole.of(userRole);
    validateRole(userRole, UserRole.MASTER, UserRole.HUB_MANAGER, UserRole.DELIVERY_MANAGER);

    switch (role) {
      case HUB_MANAGER -> validateDeliveryHubAccess(delivery, userHubId);
      case DELIVERY_MANAGER -> validateDeliveryManager(delivery, userId);
      default -> {}
    }
  }

  public void validateRouteDeliveryManagerAccess(
      DeliveryRoute route, UUID userId, String userRole, UUID userHubId
  ) {
    UserRole role = UserRole.of(userRole);
    validateRole(userRole, UserRole.MASTER, UserRole.HUB_MANAGER, UserRole.DELIVERY_MANAGER);

    switch (role) {
      case HUB_MANAGER -> validateRouteHubAccess(route, userHubId);
      case DELIVERY_MANAGER -> validateDeliveryManager(route, userId);
      default -> {}
    }
  }
}