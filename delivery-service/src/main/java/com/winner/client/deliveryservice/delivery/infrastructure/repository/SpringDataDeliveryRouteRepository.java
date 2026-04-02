package com.winner.client.deliveryservice.delivery.infrastructure.repository;

import com.winner.client.deliveryservice.delivery.domain.entity.DeliveryRoute;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataDeliveryRouteRepository extends JpaRepository<DeliveryRoute, UUID> {
  List<DeliveryRoute> findAllByDeliveryId(UUID deliveryId);
}
