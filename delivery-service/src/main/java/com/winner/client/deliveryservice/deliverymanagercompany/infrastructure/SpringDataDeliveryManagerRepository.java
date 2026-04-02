package com.winner.client.deliveryservice.deliverymanagercompany.infrastructure;

import com.winner.client.deliveryservice.deliverymanagercompany.domain.entity.DeliveryManagerCompany;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SpringDataDeliveryManagerRepository
	extends JpaRepository<DeliveryManagerCompany, UUID> {

	Optional<DeliveryManagerCompany> findTopByHubId_ValueOrderByAssignmentOrder_ValueDesc(UUID hubId);
	boolean existsByUserId_Value(UUID userId);
	Long countByHubId_ValueAndDeletedByIsNull(UUID hubId);
	Optional<DeliveryManagerCompany> findByUserId_Value(UUID userId);
}
