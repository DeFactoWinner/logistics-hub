package com.winner.client.deliveryservice.delivery.domain.repository;

import com.winner.client.deliveryservice.delivery.domain.entity.DeliveryRoute;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeliveryRouteRepository {
  List<DeliveryRoute> findAllByDeliveryId(UUID deliveryId);
  Optional<DeliveryRoute> findById(UUID id);
  void save(DeliveryRoute route);
}
