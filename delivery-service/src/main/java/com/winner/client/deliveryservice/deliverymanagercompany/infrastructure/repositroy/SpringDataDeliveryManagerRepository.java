package com.winner.client.deliveryservice.deliverymanagercompany.infrastructure.repositroy;

import com.winner.client.deliveryservice.common.constants.DeliveryManagerStatus;
import com.winner.client.deliveryservice.deliverymanagercompany.domain.entity.DeliveryManagerCompany;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SpringDataDeliveryManagerRepository
	extends JpaRepository<DeliveryManagerCompany, UUID> {

	Optional<DeliveryManagerCompany> findTopByHubId_ValueOrderByAssignmentOrder_ValueDesc(UUID hubId);
	boolean existsByUser_UserId(UUID userId);
	Long countByHubId_ValueAndDeletedByIsNull(UUID hubId);
	Optional<DeliveryManagerCompany> findByUser_UserIdAndDeletedByNull(UUID userId);
	List<DeliveryManagerCompany> findAllByHubId_ValueAndDeletedByNullAndDeliveryManagerStatus(UUID hubId, DeliveryManagerStatus status);
  Optional<DeliveryManagerCompany> findByIdAndDeletedAtNull(UUID deliveryManagerId);
}
