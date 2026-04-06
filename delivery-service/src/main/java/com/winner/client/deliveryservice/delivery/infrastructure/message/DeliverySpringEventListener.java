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
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeliverySpringEventListener {

	private final DeliveryMessageUsecase usecase;

	@EventListener
	public void handleHubAssignSuccess(AssignHubDeliveryManagerSuccessEvent event) {
		log.info("Hub 배정 성공 이벤트 수신: {}", event.deliveryId());
		usecase.completeHubDeliveryManagerAssign(DeliveryRouteAssignCompleteCommand.from(event));
	}

	@EventListener
	public void handleHubAssignFail(AssignHubDeliveryManagerFailEvent event) {
		log.error("Hub 배정 실패 이벤트 수신: {}", event.message());
		usecase.retryHubDeliveryManagerAssign(HubDeliveryManagerAssignFailCommand.from(event));
	}

	@EventListener
	public void handleHubDeliveryComplete(DeliveryCompleteEvent event) {
		log.info("Hub 배달 완료 이벤트 수신: {}", event.deliveryId());
		usecase.completeDeliveryRoute(CompleteDeliveryRouteCommand.from(event));
	}

	@EventListener
	public void handleCompanyAssignSuccess(AssignCompanyDeliveryManagerSuccessEvent event) {
		log.info("업체 배정 성공 이벤트 수신: {}", event.deliveryId());
		usecase.completeCompanyDeliveryManagerAssign(DeliveryAssignCompleteCommand.from(event));
	}

	@EventListener
	public void handleCompanyAssignFail(AssignCompanyDeliveryManagerFailEvent event) {
		log.error("업체 배정 실패 이벤트 수신 {}", event.message());
		usecase.retryCompanyDeliveryManagerAssign(CompanyDeliveryManagerAssignFailCommand.from(event));
	}

	@EventListener
	public void handleCompanyFinalComplete(DeliveryFinalCompleteEvent event) {
		log.info("최종 배달 완료 이벤트 수신: {}", event.deliveryId());
		usecase.completeDelivery(CompleteDeliveryCommand.from(event));
	}
}