package com.winner.client.deliveryservice.delivery.domain.repository;

import com.winner.client.deliveryservice.delivery.domain.entity.Delivery;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

public interface DeliveryRepository{
  Optional<Delivery> findByIdWithRoutes(UUID id);
  Optional<Delivery> findById(UUID id);
  void save(Delivery delivery);
}
