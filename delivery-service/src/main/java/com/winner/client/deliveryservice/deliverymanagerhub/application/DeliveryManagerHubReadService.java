package com.winner.client.deliveryservice.deliverymanagerhub.application;

import static com.winner.client.deliveryservice.common.exception.deliverymanager.hub.DeliveryManagerHubErrorCode.NOT_FOUND_HUB_DELIVERY_MANAGER;

import com.winner.client.deliveryservice.deliverymanagerhub.application.dto.result.DeliveryManagerHubInfoResult;
import com.winner.client.deliveryservice.deliverymanagerhub.domain.repository.DeliveryManagerHubRepository;
import com.winner.client.global.exception.BusinessException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeliveryManagerHubReadService {

	private final DeliveryManagerHubRepository repository;

	public DeliveryManagerHubInfoResult getDetail(UUID userId) {
		return DeliveryManagerHubInfoResult.from(repository.findByUserId(userId)
			.orElseThrow(() -> new BusinessException(NOT_FOUND_HUB_DELIVERY_MANAGER)));
	}
}
