package com.winner.client.deliveryservice.delivery.application.service.impl;

import com.winner.client.deliveryservice.common.event.AssignDeliveryManagerCompanyEvent;
import com.winner.client.deliveryservice.common.event.AssignDeliveryManagerHubEvent;
import com.winner.client.deliveryservice.delivery.application.dto.command.CreateDeliveryRouteCommand;
import com.winner.client.deliveryservice.delivery.application.dto.external.HubRouteInfo;
import com.winner.client.deliveryservice.delivery.application.port.DeliveryEventPort;
import com.winner.client.deliveryservice.delivery.application.reader.HubRouteReader;
import com.winner.client.deliveryservice.delivery.application.service.DeliveryAssignmentService;
import com.winner.client.deliveryservice.delivery.domain.entity.Delivery;
import com.winner.client.deliveryservice.delivery.domain.entity.DeliveryRoute;
import com.winner.client.deliveryservice.delivery.domain.repository.DeliveryRouteRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryAssignmentServiceImpl implements DeliveryAssignmentService {

  private final DeliveryRouteRepository deliveryRouteRepository;
  private final HubRouteReader hubRouteReader;
  private final DeliveryEventPort deliveryEventPort;

  @Override
  public void assignCompanyDeliveryManager(Delivery delivery) {
    deliveryEventPort.publishAssignCompanyEvent(AssignDeliveryManagerCompanyEvent.from(delivery));
  }

  @Override
  public void assignHubDeliveryManager(Delivery delivery) {
    createAndSaveHubRoutes(delivery);
    publishHubAssignEvent(delivery.getId());
  }

  @Override
  public void retryHubDeliveryManager(UUID deliveryId) {
    publishHubAssignEvent(deliveryId);
  }

  private void publishHubAssignEvent(UUID deliveryId) {
    deliveryEventPort.publishAssignHubEvent(new AssignDeliveryManagerHubEvent(deliveryId));
  }

  private void createAndSaveHubRoutes(Delivery delivery) {
    List<DeliveryRoute> routes = getDeliveryHubRoute(delivery);
    routes.forEach(deliveryRouteRepository::save);
  }

  private List<DeliveryRoute> getDeliveryHubRoute(Delivery delivery) {
    HubRouteInfo hubRouteInfo = hubRouteReader.
        getHubRoutes(delivery.getHubRoute().getOriginHubId(), delivery.getHubRoute().getDestinationHubId());

    return CreateDeliveryRouteCommand.of(delivery, hubRouteInfo)
        .stream()
        .map(DeliveryRoute::create)
        .toList();
  }

}
