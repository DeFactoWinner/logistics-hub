package com.winner.client.deliveryservice.deliverymanagercompany.domain.repository;

import com.winner.client.deliveryservice.deliverymanagercompany.domain.entity.DeliveryManagerCompany;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeliveryManagerCompanyRepository {

	DeliveryManagerCompany save(DeliveryManagerCompany deliveryManagerCompany);
	boolean existByUserId(UUID userId);
	Optional<DeliveryManagerCompany> findLastAssignmentOrderByHubId(UUID hubId);
	Long countByHubId(UUID hubId);
	Optional<DeliveryManagerCompany> findByUserIdAndDeletedByNull(UUID userId);
	Optional<DeliveryManagerCompany> findByIdAndDeletedByNull(UUID deliveryManagerId);
	List<DeliveryManagerCompany> findAllAvailableManagers(UUID hubId);
}
