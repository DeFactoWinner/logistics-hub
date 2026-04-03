package com.winner.client.deliveryservice.deliverymanagerhub.infrastructure;

import com.winner.client.deliveryservice.deliverymanagerhub.domain.entity.DeliveryManagerHub;
import com.winner.client.deliveryservice.deliverymanagerhub.domain.repository.DeliveryManagerHubRepository;
import java.util.List;
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
	public Long countByDeletedByIsNull() {
		return repository.countByDeletedByIsNull();
	}

	@Override
	public Optional<DeliveryManagerHub> findFirstByOrderByAssignmentOrderDesc() {
		return repository.findFirstByOrderByAssignmentOrder_ValueDesc();
	}

	@Override
	public Optional<DeliveryManagerHub> findByUserId(UUID userId) {
		return repository.findByUserId_ValueAndDeletedByNull(userId);
	}

	@Override
	public List<DeliveryManagerHub> findAllAvailableManagers() {
		return repository.findAllAvailableManagers();
	}
}
