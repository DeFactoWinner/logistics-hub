package com.winner.client.deliveryservice.deliverymanagerhub.infrastructure;

import com.winner.client.deliveryservice.deliverymanagerhub.domain.entity.DeliveryManagerHub;
import com.winner.client.deliveryservice.deliverymanagerhub.domain.repository.DeliveryManagerHubRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DeliveryManagerHubJpaRepository implements DeliveryManagerHubRepository {

	private final SpringDataDeliveryManagerHubRepository repository;

	@Override
	public DeliveryManagerHub save(DeliveryManagerHub deliveryManagerHub) {
		return repository.save(deliveryManagerHub);
	}

	@Override
	public boolean existByUserId(UUID userId) {
		return repository.existsByUserId_Value(userId);
	}

	@Override
	public Long count() {
		return repository.count();
	}

	@Override
	public Optional<DeliveryManagerHub> findFirstByOrderByAssignmentOrderDesc() {
		return repository.findFirstByOrderByAssignmentOrder_ValueDesc();
	}
}
