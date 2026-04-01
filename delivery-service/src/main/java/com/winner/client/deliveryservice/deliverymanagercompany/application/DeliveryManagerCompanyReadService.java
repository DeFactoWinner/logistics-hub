package com.winner.client.deliveryservice.deliverymanagercompany.application;

import static com.winner.client.deliveryservice.common.exception.deliverymanager.company.DeliveryManagerCompanyErrorCode.NOT_FOUND_COMPANY_DELIVERY_MANAGER;

import com.winner.client.deliveryservice.deliverymanagercompany.application.result.DeliveryManagerCompanyInfoResult;
import com.winner.client.deliveryservice.deliverymanagercompany.domain.repository.DeliveryManagerCompanyRepository;
import com.winner.client.global.exception.BusinessException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeliveryManagerCompanyReadService {

	private final DeliveryManagerCompanyRepository repository;

	public DeliveryManagerCompanyInfoResult getDetail(UUID id) {
		return DeliveryManagerCompanyInfoResult.from(repository.findById(id).orElseThrow(
			() -> new BusinessException(NOT_FOUND_COMPANY_DELIVERY_MANAGER)
		));
	}
}
