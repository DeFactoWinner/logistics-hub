package com.winner.client.deliveryservice.delivery.application.dto.command;

import com.winner.client.deliveryservice.delivery.domain.vo.Address;
import com.winner.client.deliveryservice.delivery.domain.vo.HubRoute;
import com.winner.client.deliveryservice.delivery.domain.vo.Receiver;
import com.winner.client.deliveryservice.delivery.presentation.dto.request.CreateDeliveryRequest;
import java.util.UUID;

public record CreateDeliveryCommand(
    UUID ordersId,
    HubRoute hubRoute,
    String originHubName,
    String destinationHubName,
    Receiver receiver,
    Address address
) {
  public static CreateDeliveryCommand from(CreateDeliveryRequest request) {
    return new CreateDeliveryCommand(
        request.ordersId(),

        new HubRoute(request.originHubId(), request.destinationHubId()),
        request.originHubName(),
        request.destinationHubName(),

        new Receiver(request.receiverId(), request.receiver(), request.slackId()),
        new Address(request.roadAddress(), request.detailAddress())
    );
  }
}
