package com.winner.client.deliveryservice.delivery.infrastructure.repository;

import com.winner.client.deliveryservice.delivery.domain.entity.Delivery;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SpringDataDeliveryRepository extends JpaRepository<Delivery, UUID> {
  @Query("SELECT d FROM Delivery d JOIN FETCH d.routes WHERE d.id = :id")
  Optional<Delivery> findByIdWithRoutes(@Param("id") UUID id);
  boolean existsByOrdersId(UUID ordersId);
}
