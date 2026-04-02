package com.winner.client.deliveryservice.deliverymanagercompany.application;

import static com.winner.client.deliveryservice.common.exception.deliverymanager.company.DeliveryManagerCompanyErrorCode.ALREADY_REGISTERED_COMPANY_DELIVERY_MANAGER;
import static com.winner.client.deliveryservice.common.exception.deliverymanager.company.DeliveryManagerCompanyErrorCode.NOT_FOUND_COMPANY_DELIVERY_MANAGER;

import com.winner.client.deliveryservice.deliverymanagercompany.application.command.DeliveryManagerCompanyRegistrationCommand;
import com.winner.client.deliveryservice.deliverymanagercompany.application.result.DeliveryManagerCompanyInfoResult;
import com.winner.client.deliveryservice.deliverymanagercompany.domain.entity.DeliveryManagerCompany;
import com.winner.client.deliveryservice.deliverymanagercompany.domain.repository.DeliveryManagerCompanyRepository;
import com.winner.client.global.exception.BusinessException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DeliveryManagerCompanyWriteService {

	private final DeliveryManagerCompanyRepository repository;

	public DeliveryManagerCompanyInfoResult registration(
		DeliveryManagerCompanyRegistrationCommand command) {

		if (repository.existByUserId(command.userId())) {
			throw new BusinessException(ALREADY_REGISTERED_COMPANY_DELIVERY_MANAGER);
		}

		Long curCount = repository.countByHubId(command.hubId());

		Long nextAssignmentOrder = repository.findLastAssignmentOrderByHubId(
				command.hubId()).map(last -> last.getAssignmentOrder() + 1)
			.orElse(1L);

		return DeliveryManagerCompanyInfoResult.from(
			repository.save(
				DeliveryManagerCompany.create(command.userId(), command.hubId(), nextAssignmentOrder, curCount)
			));
	}

	public DeliveryManagerCompanyInfoResult switchStatus(UUID id) {
		DeliveryManagerCompany deliveryManagerCompany = repository.findById(id).orElseThrow(
				() -> new BusinessException(NOT_FOUND_COMPANY_DELIVERY_MANAGER)
			);
		deliveryManagerCompany.switchStatus();
		return DeliveryManagerCompanyInfoResult.from(deliveryManagerCompany);
	}
}
