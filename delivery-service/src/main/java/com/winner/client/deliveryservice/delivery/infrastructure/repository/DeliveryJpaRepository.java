package com.winner.client.deliveryservice.delivery.infrastructure.repository;

import com.winner.client.deliveryservice.delivery.domain.entity.Delivery;
import com.winner.client.deliveryservice.delivery.domain.repository.DeliveryRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DeliveryJpaRepository implements DeliveryRepository {

  private final SpringDataDeliveryRepository deliveryJpaRepository;

  @Override
  public Optional<Delivery> findByIdWithRoutes(UUID id) {
    return deliveryJpaRepository.findByIdWithRoutes(id);
  }

  @Override
  public Optional<Delivery> findById(UUID id) {
    return deliveryJpaRepository.findById(id);
  }

  @Override
  public void save(Delivery delivery) {
    deliveryJpaRepository.save(delivery);
  }
}
