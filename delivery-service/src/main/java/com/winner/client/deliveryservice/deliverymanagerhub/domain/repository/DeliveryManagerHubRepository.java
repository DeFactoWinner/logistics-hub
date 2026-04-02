package com.winner.client.deliveryservice.deliverymanagerhub.domain.repository;

import com.winner.client.deliveryservice.deliverymanagerhub.domain.entity.DeliveryManagerHub;
import java.util.Optional;
import java.util.UUID;

public interface DeliveryManagerHubRepository {

	DeliveryManagerHub save(DeliveryManagerHub deliveryManagerHub);
	boolean existByUserId(UUID userId);
	Long count();
	Optional<DeliveryManagerHub> findFirstByOrderByAssignmentOrderDesc();
}
