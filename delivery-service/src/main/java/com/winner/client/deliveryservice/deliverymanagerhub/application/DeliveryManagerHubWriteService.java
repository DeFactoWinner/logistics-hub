package com.winner.client.deliveryservice.deliverymanagerhub.application;

import static com.winner.client.deliveryservice.common.exception.deliverymanager.hub.DeliveryManagerHubErrorCode.ALREADY_REGISTERED_HUB_DELIVERY_MANAGER;
import static com.winner.client.deliveryservice.common.exception.deliverymanager.hub.DeliveryManagerHubErrorCode.NOT_FOUND_AVAILABLE_HUB_DELIVERY_MANAGERS;
import static com.winner.client.deliveryservice.common.exception.deliverymanager.hub.DeliveryManagerHubErrorCode.NOT_FOUND_HUB_DELIVERY_MANAGER;

import com.winner.client.deliveryservice.deliverymanagerhub.application.dto.commnad.DeliveryManagerAssignEventCommand;
import com.winner.client.deliveryservice.deliverymanagerhub.application.dto.commnad.DeliveryManagerHubRegistrationCommand;
import com.winner.client.deliveryservice.deliverymanagerhub.application.dto.result.DeliveryAssignResult;
import com.winner.client.deliveryservice.deliverymanagerhub.application.dto.result.DeliveryCompleteResult;
import com.winner.client.deliveryservice.deliverymanagerhub.application.message.DeliveryDeliveryManagerHubUsecase;
import com.winner.client.deliveryservice.deliverymanagerhub.application.message.DeliveryMessagePort;
import com.winner.client.deliveryservice.deliverymanagerhub.application.dto.result.DeliveryManagerHubInfoResult;
import com.winner.client.deliveryservice.deliverymanagerhub.domain.entity.DeliveryManagerHub;
import com.winner.client.deliveryservice.deliverymanagerhub.domain.repository.DeliveryManagerHubRepository;
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
public class DeliveryManagerHubWriteService implements DeliveryDeliveryManagerHubUsecase {

	private final DeliveryManagerHubRepository repository;
	private final DeliveryMessagePort deliveryMessagePort;

	public DeliveryManagerHubInfoResult registration(
		DeliveryManagerHubRegistrationCommand command) {

		if (repository.existByUserId(command.userId())) {
			throw new BusinessException(ALREADY_REGISTERED_HUB_DELIVERY_MANAGER);
		}

		Long curCount = repository.countByDeletedByIsNull();

		Long nextAssignmentOrder = repository.findFirstByOrderByAssignmentOrderDesc()
			.map(last -> last.getAssignmentOrder() + 1)
			.orElse(1L);

		return DeliveryManagerHubInfoResult.from(
			repository.save(
				DeliveryManagerHub.create(command.userId(), command.name(), nextAssignmentOrder, curCount)
			)
		);
	}

	public DeliveryManagerHubInfoResult switchStatus(UUID userId) {
		DeliveryManagerHub deliveryManagerHub = repository.findByUserId(userId)
			.orElseThrow(() -> new BusinessException(NOT_FOUND_HUB_DELIVERY_MANAGER));

		deliveryManagerHub.switchStatus();
		return DeliveryManagerHubInfoResult.from(deliveryManagerHub);
	}

	public void deactivate(UUID userId) {
		DeliveryManagerHub deliveryManagerHub = repository.findByUserId(userId)
			.orElseThrow(() -> new BusinessException(NOT_FOUND_HUB_DELIVERY_MANAGER));
		deliveryManagerHub.softDelete(userId);
	}

	@Override
	public void assignHubDeliveryManager(DeliveryManagerAssignEventCommand command) {
		try {
			DeliveryManagerHub manager = selectAvailableManager();
			manager.assignDelivery(command.deliveryId());
			deliveryMessagePort
				.assignEventPublish(
					DeliveryAssignResult
						.success(command.deliveryId(), manager.getId(), manager.getName()));
		} catch (BusinessException e) {
			log.warn("DeliveryManager assignment failure : {}", e.getMessage());
			deliveryMessagePort.assignEventPublish(DeliveryAssignResult.fail(e.getMessage()));
		}
	}

	private DeliveryManagerHub selectAvailableManager() {
		//todo: 현재 배송 가능한 담당자중 가장 알맞은 담당자에게 배정되는 알고리즘 필요 ex. 최근 배송기록이 가장 오래된 담당자로 배정
		return repository.findAllAvailableManagers()
			.stream()
			.findFirst()
			.orElseThrow(() -> new BusinessException(NOT_FOUND_AVAILABLE_HUB_DELIVERY_MANAGERS));
	}

	public void completion(UUID userId, UUID deliveryId) {
		DeliveryManagerHub manager = repository.findByUserId(userId)
			.orElseThrow(() -> new BusinessException(NOT_FOUND_HUB_DELIVERY_MANAGER));
		manager.completeDelivery(deliveryId);
		deliveryMessagePort.deliveryCompleteEventPublish(DeliveryCompleteResult.of(userId, deliveryId));
	}
}
