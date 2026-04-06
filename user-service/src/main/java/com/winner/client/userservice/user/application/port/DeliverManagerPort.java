package com.winner.client.userservice.user.application.port;

import com.winner.client.userservice.user.domain.entity.User;

public interface DeliverManagerPort {

  void deleteDeliveryManager(User user);

  void registrationDeliveryManager(User user);
}
