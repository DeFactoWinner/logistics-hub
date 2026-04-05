package com.winner.client.deliveryservice.delivery.application.service;

import com.winner.client.deliveryservice.delivery.application.dto.command.CompanyDeliveryManagerAssignFailCommand;
import com.winner.client.deliveryservice.delivery.application.dto.command.CompleteDeliveryCommand;
import com.winner.client.deliveryservice.delivery.application.dto.command.CompleteDeliveryRouteCommand;
import com.winner.client.deliveryservice.delivery.application.dto.command.DeliveryAssignCompleteCommand;
import com.winner.client.deliveryservice.delivery.application.dto.command.HubDeliveryManagerAssignFailCommand;
import com.winner.client.deliveryservice.delivery.application.dto.command.DeliveryRouteAssignCompleteCommand;

public interface DeliveryMessageUsecase {
  void completeHubDeliveryManagerAssign(DeliveryRouteAssignCompleteCommand command);
  void completeCompanyDeliveryManagerAssign(DeliveryAssignCompleteCommand command);
  void retryHubDeliveryManagerAssign(HubDeliveryManagerAssignFailCommand command);
  void retryCompanyDeliveryManagerAssign(CompanyDeliveryManagerAssignFailCommand command);

  void completeDeliveryRoute(CompleteDeliveryRouteCommand command);
  void completeDelivery(CompleteDeliveryCommand command);
}
