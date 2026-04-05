package com.winner.client.deliveryservice.delivery.application.service.impl;

import com.winner.client.deliveryservice.common.event.AssignDeliveryManagerCompanyEvent;
import com.winner.client.deliveryservice.common.event.AssignDeliveryManagerHubEvent;
import com.winner.client.deliveryservice.common.exception.delivery.DeliveryErrorCode;
import com.winner.client.deliveryservice.delivery.application.dto.command.CreateDeliveryRouteCommand;
import com.winner.client.deliveryservice.delivery.application.dto.external.HubRouteInfo;
import com.winner.client.deliveryservice.delivery.application.port.DeliveryEventPort;
import com.winner.client.deliveryservice.delivery.application.port.HubRoutePort;
import com.winner.client.deliveryservice.delivery.application.service.DeliveryAssignmentService;
import com.winner.client.deliveryservice.delivery.domain.entity.Delivery;
import com.winner.client.deliveryservice.delivery.domain.entity.DeliveryRoute;
import com.winner.client.deliveryservice.delivery.domain.repository.DeliveryRouteRepository;
import com.winner.client.global.exception.BusinessException;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryAssignmentServiceImpl implements DeliveryAssignmentService {

  private final DeliveryRouteRepository deliveryRouteRepository;
  private final HubRoutePort hubRoutePort;
  private final DeliveryEventPort deliveryEventPort;

  @Override
  public void assignCompanyDeliveryManager(Delivery delivery) {
    deliveryEventPort.publishAssignCompanyEvent(AssignDeliveryManagerCompanyEvent.of(delivery));
  }

  @Override
  public void assignHubDeliveryManager(Delivery delivery) {
    List<DeliveryRoute> routes = createAndSaveHubRoutes(delivery);

    DeliveryRoute firstRoute = getFirstRoute(routes);

    deliveryEventPort.publishAssignHubEvent(AssignDeliveryManagerHubEvent.of(delivery, firstRoute));
  }

  @Override
  public void retryHubDeliveryManager(DeliveryRoute failedRoute) {
    deliveryEventPort.publishAssignHubEvent(
        AssignDeliveryManagerHubEvent.of(failedRoute));
  }

  private List<DeliveryRoute> createAndSaveHubRoutes(Delivery delivery) {
    List<DeliveryRoute> routes = getDeliveryHubRoute(delivery);
    routes.forEach(deliveryRouteRepository::save);
    return routes;
  }

  private List<DeliveryRoute> getDeliveryHubRoute(Delivery delivery) {
    HubRouteInfo hubRouteInfo = hubRoutePort.
        getHubRoutes(delivery.getHubRoute().getOriginHubId(), delivery.getHubRoute().getDestinationHubId());

    return CreateDeliveryRouteCommand.of(delivery, hubRouteInfo)
        .stream()
        .map(DeliveryRoute::create)
        .toList();
  }

  private DeliveryRoute getFirstRoute(List<DeliveryRoute> routes) {
    return routes.stream()
        .min(Comparator.comparingInt(DeliveryRoute::getSeq))
        .orElseThrow(() -> new BusinessException(DeliveryErrorCode.NOT_FOUND_DELIVERY_ROUTE));
  }
}
