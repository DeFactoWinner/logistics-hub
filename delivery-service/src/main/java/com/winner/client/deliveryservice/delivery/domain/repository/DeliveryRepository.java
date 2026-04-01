package com.winner.client.deliveryservice.delivery.domain.repository;

import com.winner.client.deliveryservice.delivery.domain.entity.Delivery;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, UUID> {
}
