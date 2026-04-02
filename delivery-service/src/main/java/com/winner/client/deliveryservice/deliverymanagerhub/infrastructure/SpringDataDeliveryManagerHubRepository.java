package com.winner.client.deliveryservice.deliverymanagerhub.infrastructure;

import com.winner.client.deliveryservice.deliverymanagerhub.domain.entity.DeliveryManagerHub;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataDeliveryManagerHubRepository extends JpaRepository<DeliveryManagerHub, UUID> {

	boolean existsByUserId_Value(UUID userId);
	Optional<DeliveryManagerHub> findFirstByOrderByAssignmentOrder_ValueDesc();
}
