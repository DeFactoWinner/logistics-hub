package com.winner.client.userservice.user.infrastructure.client;

import com.winner.client.global.exception.BusinessException;
import com.winner.client.userservice.common.exception.UserErrorCode;
import com.winner.client.userservice.user.application.port.DeliverManagerPort;
import com.winner.client.userservice.user.domain.entity.User;
import com.winner.client.userservice.user.infrastructure.client.dto.DeliveryManageHubRegistrationRequest;
import com.winner.client.userservice.user.infrastructure.client.dto.DeliveryManagerCompanyRegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryManagerClientAdapter implements DeliverManagerPort {

  private final DeliveryManagerClient deliveryManagerClient;

  @Override
  public void deleteDeliveryManager(User user) {
    try {
      if (user.getReferenceId() == null) {
        deliveryManagerClient.deleteDeliveryHubManger(user.getId());
      } else {
        deliveryManagerClient.deleteDeliveryCompanyManger(user.getId());
      }
    } catch (Exception e) {
      throw new BusinessException(UserErrorCode.ACTIVE_USER_CANNOT_WITHDRAW);
    }
  }

  @Override
  public void registrationDeliveryManager(User user) {
    try {
      if (user.getReferenceId() == null) {
        deliveryManagerClient.registrationDeliveryHubManager(
            DeliveryManageHubRegistrationRequest.from(user));
      } else {
        deliveryManagerClient.registrationDeliveryCompanyManager(
            DeliveryManagerCompanyRegistrationRequest.from(user));
      }
    } catch (Exception e) {
      throw new BusinessException(UserErrorCode.ACTIVE_USER_CANNOT_WITHDRAW);
    }
  }

}
