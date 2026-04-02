package com.winner.client.deliveryservice.deliverymanagercompany.infrastructure;

import com.winner.client.deliveryservice.deliverymanagercompany.domain.entity.DeliveryManagerCompany;
import com.winner.client.deliveryservice.deliverymanagercompany.domain.repository.DeliveryManagerCompanyRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DeliveryManagerCompanyJpaRepository implements
	DeliveryManagerCompanyRepository {

	private final SpringDataDeliveryManagerRepository jpaRepository;


	@Override
	public DeliveryManagerCompany save(DeliveryManagerCompany deliveryManagerCompany) {
		return jpaRepository.save(deliveryManagerCompany);
	}

	@Override
	public boolean existByUserId(UUID userId) {
		return jpaRepository.existsByUserId_Value(userId);
	}

	@Override
	public Optional<DeliveryManagerCompany> findLastAssignmentOrderByHubId(UUID hubId) {
		return jpaRepository.findTopByHubId_ValueOrderByAssignmentOrder_ValueDesc(hubId);
	}

	@Override
	public Long countByHubId(UUID hubId) {
		return jpaRepository.countByHubId_Value(hubId);
	}

	@Override
	public Optional<DeliveryManagerCompany> findById(UUID id) {
		return jpaRepository.findById(id);
	}

	@Override
	public Optional<DeliveryManagerCompany> findByUserId(UUID userId) {
		return jpaRepository.findByUserId_Value(userId);
	}
}
