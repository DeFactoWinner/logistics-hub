package com.winner.orderservice.order.infrastructure.repository;

import com.winner.orderservice.order.domain.entity.Order;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, UUID>, JpaSpecificationExecutor<Order> {

  @Query("SELECT o FROM Order o WHERE o.id = :id AND o.deletedAt IS NULL")
  Optional<Order> findActiveById(@Param("id") UUID id);
}

