package com.winner.client.deliveryservice.deliverymanagerhub.application;

import static com.winner.client.deliveryservice.common.exception.deliverymanager.hub.DeliveryManagerHubErrorCode.ALREADY_REGISTERED_HUB_DELIVERY_MANAGER;

import com.winner.client.deliveryservice.deliverymanagerhub.application.commnad.DeliveryManagerHubRegistrationCommand;
import com.winner.client.deliveryservice.deliverymanagerhub.application.result.DeliveryManagerHubInfoResult;
import com.winner.client.deliveryservice.deliverymanagerhub.domain.entity.DeliveryManagerHub;
import com.winner.client.deliveryservice.deliverymanagerhub.domain.repository.DeliveryManagerHubRepository;
import com.winner.client.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryManagerHubWriteService {

	private final DeliveryManagerHubRepository repository;

	public DeliveryManagerHubInfoResult registration(
		DeliveryManagerHubRegistrationCommand command) {

		if (repository.existByUserId(command.userId())) {
			throw new BusinessException(ALREADY_REGISTERED_HUB_DELIVERY_MANAGER);
		}

		Long curCount = repository.count();

		Long nextAssignmentOrder = repository.findFirstAssignmentOrderOrderByDesc()
			.map(last -> last.getAssignmentOrder() + 1)
			.orElse(1L);

		return DeliveryManagerHubInfoResult.from(
			repository.save(
				DeliveryManagerHub.create(command.userId(), nextAssignmentOrder, curCount)
			)
		);
	}
}
