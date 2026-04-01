package com.winner.client.deliveryservice.delivery.application.service.impl;

import com.winner.client.deliveryservice.common.exception.delivery.DeliveryErrorCode;
import com.winner.client.deliveryservice.delivery.application.service.DeliveryCommendService;
import com.winner.client.deliveryservice.delivery.domain.entity.DeliveryRoute;
import com.winner.client.deliveryservice.delivery.domain.repository.DeliveryRouteRepository;
import com.winner.client.deliveryservice.delivery.presentation.dto.request.UpdateDeliveryRequest;
import com.winner.client.deliveryservice.delivery.presentation.dto.response.DeliveryRouteCommandResponse;
import com.winner.client.global.exception.BusinessException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryCommendServiceImpl implements DeliveryCommendService {

  private final DeliveryRouteRepository deliveryRouteRepository;

  @Override
  public DeliveryRouteCommandResponse updateActualDeliveryRouteInfo(
      UUID deliveryRouteId, UpdateDeliveryRequest request, UUID userId) {
    DeliveryRoute route = deliveryRouteRepository.findById(deliveryRouteId)
        .orElseThrow(()-> new BusinessException(DeliveryErrorCode.NOT_FOUND_DELIVERY_ROUTE));

    route.updateActualDeliveryRouteInfo(request.actualDistance(), request.actualArrivalTime());

    return DeliveryRouteCommandResponse.from(route);
  }

}
