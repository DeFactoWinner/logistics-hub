package com.winner.client.deliveryservice.delivery.infrastructure.message;

import com.winner.client.deliveryservice.common.event.deliverymanager.company.AssignCompanyDeliveryManagerFailEvent;
import com.winner.client.deliveryservice.common.event.deliverymanager.company.AssignCompanyDeliveryManagerSuccessEvent;
import com.winner.client.deliveryservice.common.event.deliverymanager.company.DeliveryFinalCompleteEvent;
import com.winner.client.deliveryservice.common.event.deliverymanager.hub.AssignHubDeliveryManagerFailEvent;
import com.winner.client.deliveryservice.common.event.deliverymanager.hub.AssignHubDeliveryManagerSuccessEvent;
import com.winner.client.deliveryservice.common.event.deliverymanager.hub.DeliveryCompleteEvent;
import com.winner.client.deliveryservice.delivery.application.dto.command.CompanyDeliveryManagerAssignFailCommand;
import com.winner.client.deliveryservice.delivery.application.dto.command.CompleteDeliveryCommand;
import com.winner.client.deliveryservice.delivery.application.dto.command.CompleteDeliveryRouteCommand;
import com.winner.client.deliveryservice.delivery.application.dto.command.DeliveryAssignCompleteCommand;
import com.winner.client.deliveryservice.delivery.application.dto.command.DeliveryRouteAssignCompleteCommand;
import com.winner.client.deliveryservice.delivery.application.dto.command.HubDeliveryManagerAssignFailCommand;
import com.winner.client.deliveryservice.delivery.application.service.DeliveryMessageUsecase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeliverySpringEventListener {

	private final DeliveryMessageUsecase usecase;

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void handleHubAssignSuccess(AssignHubDeliveryManagerSuccessEvent event) {
		log.info("Hub 배정 성공 이벤트 수신: {}", event.deliveryId());
		usecase.completeHubDeliveryManagerAssign(DeliveryRouteAssignCompleteCommand.from(event));
	}

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void handleHubAssignFail(AssignHubDeliveryManagerFailEvent event) {
		log.error("Hub 배정 실패 이벤트 수신: {}", event.message());
		usecase.retryHubDeliveryManagerAssign(HubDeliveryManagerAssignFailCommand.from(event));
	}

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void handleHubDeliveryComplete(DeliveryCompleteEvent event) {
		log.info("Hub 배달 완료 이벤트 수신: {}", event.deliveryId());
		usecase.completeDeliveryRoute(CompleteDeliveryRouteCommand.from(event));
	}

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void handleCompanyAssignSuccess(AssignCompanyDeliveryManagerSuccessEvent event) {
		log.info("업체 배정 성공 이벤트 수신: {}", event.deliveryId());
		usecase.completeCompanyDeliveryManagerAssign(DeliveryAssignCompleteCommand.from(event));
	}

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void handleCompanyAssignFail(AssignCompanyDeliveryManagerFailEvent event) {
		log.error("업체 배정 실패 이벤트 수신 {}", event.message());
		usecase.retryCompanyDeliveryManagerAssign(CompanyDeliveryManagerAssignFailCommand.from(event));
	}

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void handleCompanyFinalComplete(DeliveryFinalCompleteEvent event) {
		log.info("최종 배달 완료 이벤트 수신: {}", event.deliveryId());
		usecase.completeDelivery(CompleteDeliveryCommand.from(event));
	}
}