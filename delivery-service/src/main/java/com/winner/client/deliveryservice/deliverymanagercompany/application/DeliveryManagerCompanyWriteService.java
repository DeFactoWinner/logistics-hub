package com.winner.client.deliveryservice.deliverymanagercompany.application;

import static com.winner.client.deliveryservice.common.exception.deliverymanager.company.DeliveryManagerCompanyErrorCode.ALREADY_REGISTERED_COMPANY_DELIVERY_MANAGER;
import static com.winner.client.deliveryservice.common.exception.deliverymanager.company.DeliveryManagerCompanyErrorCode.NOT_FOUND_AVAILABLE_COMPANY_DELIVERY_MANAGER;
import static com.winner.client.deliveryservice.common.exception.deliverymanager.company.DeliveryManagerCompanyErrorCode.NOT_FOUND_COMPANY_DELIVERY_MANAGER;

import com.winner.client.deliveryservice.deliverymanagercompany.application.dto.command.DeliveryManagerAssignEventCommand;
import com.winner.client.deliveryservice.deliverymanagercompany.application.dto.command.DeliveryManagerCompanyRegistrationCommand;
import com.winner.client.deliveryservice.deliverymanagercompany.application.dto.re.CompanyDeliveryManagerAssignResult;
import com.winner.client.deliveryservice.deliverymanagercompany.application.dto.result.DeliveryManagerCompanyInfoResult;
import com.winner.client.deliveryservice.deliverymanagercompany.application.message.CompanyDeliveryManagerExternalPort;
import com.winner.client.deliveryservice.deliverymanagercompany.application.message.DeliveryDeliveryManagerCompanyUsecase;
import com.winner.client.deliveryservice.deliverymanagercompany.domain.entity.DeliveryManagerCompany;
import com.winner.client.deliveryservice.deliverymanagercompany.domain.repository.DeliveryManagerCompanyRepository;
import com.winner.client.global.exception.BusinessException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DeliveryManagerCompanyWriteService implements DeliveryDeliveryManagerCompanyUsecase {

	private final DeliveryManagerCompanyRepository repository;
	private final CompanyDeliveryManagerExternalPort companyDeliveryManagerExternalPort;

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
				DeliveryManagerCompany.create(command.userId(), command.name(), command.hubId(), nextAssignmentOrder, curCount)
			));
	}

	public DeliveryManagerCompanyInfoResult switchStatus(UUID userId) {
		DeliveryManagerCompany deliveryManagerCompany = repository.findByUserIdAndDeletedByNull(userId).orElseThrow(
				() -> new BusinessException(NOT_FOUND_COMPANY_DELIVERY_MANAGER)
			);
		deliveryManagerCompany.switchStatus();
		return DeliveryManagerCompanyInfoResult.from(deliveryManagerCompany);
	}

	public void deactivate(UUID userId) {
		DeliveryManagerCompany deliveryManagerCompany = repository.findByUserIdAndDeletedByNull(userId)
			.orElseThrow(() -> new BusinessException(NOT_FOUND_COMPANY_DELIVERY_MANAGER));
		deliveryManagerCompany.softDelete(userId);
	}

	@Override
	public void assignHubDeliveryManager(DeliveryManagerAssignEventCommand command) {
		try {
			DeliveryManagerCompany manager = selectAvailableManager(command.hubId());
			manager.assignDelivery(command.deliveryId());
			companyDeliveryManagerExternalPort
				.assignEventPublish(
					CompanyDeliveryManagerAssignResult.success(
						command.deliveryId(),
						manager.getId(),
						manager.getName()
					));
		} catch (BusinessException e) {
			log.warn("DeliveryManager assignment failure : {}", e.getMessage());
			companyDeliveryManagerExternalPort
				.assignEventPublish(CompanyDeliveryManagerAssignResult.fail(e.getMessage()));
		}
	}

	private DeliveryManagerCompany selectAvailableManager(UUID hubId) {
		return repository.findAllAvailableManagers(hubId)
			.stream()
			.findFirst()
			.orElseThrow(() -> new BusinessException(NOT_FOUND_AVAILABLE_COMPANY_DELIVERY_MANAGER));
	}
}
