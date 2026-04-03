package com.winner.client.deliveryservice.deliverymanagerhub.infrastructure;

import com.winner.client.deliveryservice.deliverymanagerhub.domain.entity.DeliveryManagerHub;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SpringDataDeliveryManagerHubRepository extends JpaRepository<DeliveryManagerHub, UUID> {

	boolean existsByUserId_Value(UUID userId);
	Optional<DeliveryManagerHub> findFirstByOrderByAssignmentOrder_ValueDesc();
	Long countByDeletedByIsNull();
	Optional<DeliveryManagerHub> findByUserId_ValueAndDeletedByNull(UUID userId);
	@Query("SELECT d FROM DeliveryManagerHub d " +
		"WHERE d.deletedBy IS NULL " +
		"AND d.deliveryManagerStatus = 'AVAILABLE'" +
		"ORDER BY d.assignmentOrder.value ASC")
	List<DeliveryManagerHub> findAllAvailableManagers();
}
