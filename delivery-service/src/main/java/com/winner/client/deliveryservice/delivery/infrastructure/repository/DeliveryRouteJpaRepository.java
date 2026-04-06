package com.winner.client.deliveryservice.delivery.infrastructure.repository;

import com.winner.client.deliveryservice.delivery.domain.entity.DeliveryRoute;
import com.winner.client.deliveryservice.delivery.domain.repository.DeliveryRouteRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DeliveryRouteJpaRepository implements DeliveryRouteRepository {
  private final SpringDataDeliveryRouteRepository routeJpaRepository;

  @Override
  public List<DeliveryRoute> findAllByDeliveryId(UUID deliveryId) {
    return routeJpaRepository.findAllByDeliveryId(deliveryId);
  }

  @Override
  public Optional<DeliveryRoute> findById(UUID id) {
    return routeJpaRepository.findById(id);
  }

  @Override
  public Optional<DeliveryRoute> findFirstWaitingRoute(UUID deliveryId) {
    return routeJpaRepository.findFirstWaitingRoute(deliveryId);
  }

  @Override
  public Optional<DeliveryRoute> findFirstInProgressRoute(UUID deliveryId) {
    return routeJpaRepository.findFirstInProgressRoute(deliveryId);
  }

  @Override
  public void softDeleteAllByDeliveryId(UUID deliveryId, UUID userId, LocalDateTime now) {
    routeJpaRepository.softDeleteAllByDeliveryId(deliveryId, userId, now);
  }

  @Override
  public void save(DeliveryRoute route) {
    routeJpaRepository.save(route);
  }

  @Override
  public void saveAll(List<DeliveryRoute> routes) {
    routeJpaRepository.saveAll(routes);
  }
}
