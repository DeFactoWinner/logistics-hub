package com.winner.client.deliveryservice.delivery.infrastructure.repository;

import com.winner.client.deliveryservice.delivery.domain.entity.DeliveryRoute;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SpringDataDeliveryRouteRepository extends JpaRepository<DeliveryRoute, UUID> {
  List<DeliveryRoute> findAllByDeliveryId(UUID deliveryId);

  @Query("""
    SELECT dr FROM DeliveryRoute dr
    JOIN FETCH dr.delivery d
    WHERE d.id = :deliveryId
    AND dr.status = 'WAITING'
    ORDER BY dr.seq ASC
    LIMIT 1
    """)
  Optional<DeliveryRoute> findFirstWaitingRoute(@Param("deliveryId") UUID deliveryId);

  @Query("""
    SELECT dr FROM DeliveryRoute dr
    WHERE dr.delivery.id = :deliveryId
    AND dr.status = 'IN_PROGRESS'
    ORDER BY dr.seq ASC
    LIMIT 1
    """)
  Optional<DeliveryRoute> findFirstInProgressRoute(@Param("deliveryId") UUID deliveryId);

  @Modifying(clearAutomatically = true)
  @Query("""
    UPDATE DeliveryRoute r
    SET r.deletedAt = :now, r.deletedBy = :userId 
    WHERE r.delivery.id = :deliveryId AND r.deletedAt IS NULL
  """)
  void softDeleteAllByDeliveryId(
      @Param("deliveryId") UUID deliveryId,
      @Param("userId") UUID userId,
      @Param("now") LocalDateTime now
  );

}
