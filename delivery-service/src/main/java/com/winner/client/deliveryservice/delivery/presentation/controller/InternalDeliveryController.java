package com.winner.client.deliveryservice.delivery.presentation.controller;

import com.winner.client.deliveryservice.delivery.application.dto.command.CreateDeliveryCommand;
import com.winner.client.deliveryservice.delivery.application.dto.result.CreateDeliveryResult;
import com.winner.client.deliveryservice.delivery.application.service.DeliveryCommandService;
import com.winner.client.deliveryservice.delivery.presentation.dto.request.CreateDeliveryRequest;
import com.winner.client.deliveryservice.delivery.presentation.dto.response.CreateDeliveryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/v1/deliveries")
public class InternalDeliveryController {

  private final DeliveryCommandService deliveryCommandService;

  @PostMapping("/shortest")
  public CreateDeliveryResponse createDelivery(CreateDeliveryRequest request) {
    CreateDeliveryCommand command = CreateDeliveryCommand.from(request);
    CreateDeliveryResult result = deliveryCommandService.createDelivery(command);
    return CreateDeliveryResponse.from(result);
  }
}
