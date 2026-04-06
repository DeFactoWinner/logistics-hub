package com.winner.client.deliveryservice.delivery.application.service.impl;

import com.winner.client.deliveryservice.common.event.AssignDeliveryManagerCompanyEvent;
import com.winner.client.deliveryservice.common.event.AssignDeliveryManagerHubEvent;
import com.winner.client.deliveryservice.delivery.application.port.DeliveryEventPort;
import com.winner.client.deliveryservice.delivery.application.service.DeliveryAssignmentService;
import com.winner.client.deliveryservice.delivery.domain.entity.Delivery;
import com.winner.client.deliveryservice.delivery.domain.entity.DeliveryRoute;
import com.winner.client.deliveryservice.delivery.domain.repository.DeliveryRepository;
import com.winner.client.deliveryservice.delivery.domain.repository.DeliveryRouteRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeliveryAssignmentServiceImpl implements DeliveryAssignmentService {

  private final DeliveryRouteRepository deliveryRouteRepository;
  private final DeliveryRepository deliveryRepository;
  private final DeliveryEventPort deliveryEventPort;

  @Override
  @Transactional
  public void assignHubDeliveryManager(Delivery delivery, List<DeliveryRoute> routes) {
    deliveryRepository.save(delivery);
    routes.forEach(deliveryRouteRepository::save);
    publishHubAssignEvent(delivery.getId());
  }

  @Override
  @Transactional
  public void assignCompanyDeliveryManager(Delivery delivery) {
    deliveryRepository.save(delivery);
    deliveryEventPort.publishAssignCompanyEvent(AssignDeliveryManagerCompanyEvent.from(delivery));
  }

  @Override
  @Transactional
  public void retryHubDeliveryManager(UUID deliveryId) {
    publishHubAssignEvent(deliveryId);
  }

  private void publishHubAssignEvent(UUID deliveryId) {
    deliveryEventPort.publishAssignHubEvent(new AssignDeliveryManagerHubEvent(deliveryId));
  }

}
