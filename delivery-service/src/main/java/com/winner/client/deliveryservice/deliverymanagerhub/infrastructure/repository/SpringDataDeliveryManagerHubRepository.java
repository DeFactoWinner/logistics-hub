package com.winner.client.deliveryservice.deliverymanagerhub.infrastructure.repository;

import com.winner.client.deliveryservice.deliverymanagerhub.domain.entity.DeliveryManagerHub;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SpringDataDeliveryManagerHubRepository extends JpaRepository<DeliveryManagerHub, UUID> {

	boolean existsByUser_UserId(UUID userId);
	Optional<DeliveryManagerHub> findFirstByOrderByAssignmentOrder_ValueDesc();
	Long countByDeletedByIsNull();
	Optional<DeliveryManagerHub> findByUser_UserIdAndDeletedByNull(UUID userId);
	Optional<DeliveryManagerHub> findByIdAndDeletedAtNull(UUID userId);
	@Query("SELECT d FROM DeliveryManagerHub d " +
		"WHERE d.deletedBy IS NULL " +
		"AND d.deliveryManagerStatus = 'AVAILABLE'" +
		"ORDER BY d.assignmentOrder.value ASC")
	List<DeliveryManagerHub> findAllAvailableManagers();
}
