package com.winner.client.deliveryservice.delivery.domain.repository;

import com.winner.client.deliveryservice.delivery.domain.entity.DeliveryRoute;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRouteRepository extends JpaRepository<DeliveryRoute, UUID> {
  List<DeliveryRoute> findAllByDeliveryId(UUID deliveryId);
}
