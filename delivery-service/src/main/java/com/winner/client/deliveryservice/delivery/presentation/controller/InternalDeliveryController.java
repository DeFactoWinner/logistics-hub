package com.winner.client.deliveryservice.delivery.presentation.controller;

import com.winner.client.deliveryservice.delivery.application.dto.command.CreateDeliveryCommand;
import com.winner.client.deliveryservice.delivery.application.service.DeliveryCommandService;
import com.winner.client.deliveryservice.delivery.presentation.dto.request.CreateDeliveryRequest;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/v1/delivery")
public class InternalDeliveryController {

  private final DeliveryCommandService deliveryCommandService;

  @PostMapping
  public UUID createDelivery(CreateDeliveryRequest request) {
    CreateDeliveryCommand command = CreateDeliveryCommand.from(request);
    return deliveryCommandService.createDelivery(command);
  }
}
