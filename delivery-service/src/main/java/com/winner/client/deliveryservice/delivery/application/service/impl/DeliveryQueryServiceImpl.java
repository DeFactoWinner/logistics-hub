package com.winner.client.deliveryservice.delivery.application.service.impl;

import com.winner.client.deliveryservice.common.exception.delivery.DeliveryErrorCode;
import com.winner.client.deliveryservice.delivery.application.dto.query.SearchDeliveryQuery;
import com.winner.client.deliveryservice.delivery.application.dto.result.FindDeliveryResult;
import com.winner.client.deliveryservice.delivery.application.dto.result.FindDeliveryRouteResult;
import com.winner.client.deliveryservice.delivery.application.dto.result.SearchDeliveryResult;
import com.winner.client.deliveryservice.delivery.application.service.DeliveryQueryService;
import com.winner.client.deliveryservice.delivery.domain.entity.Delivery;
import com.winner.client.deliveryservice.delivery.domain.entity.DeliveryRoute;
import com.winner.client.deliveryservice.delivery.domain.repository.DeliveryRepository;
import com.winner.client.deliveryservice.delivery.domain.repository.DeliveryRouteRepository;
import com.winner.client.deliveryservice.delivery.infrastructure.repository.custom.DeliveryCustomRepository;
import com.winner.client.deliveryservice.delivery.presentation.dto.response.DeliveryInfoResponse;
import com.winner.client.deliveryservice.delivery.presentation.dto.response.DeliveryRouteInfoResponse;
import com.winner.client.deliveryservice.delivery.presentation.dto.response.GetDeliveryResponse;
import com.winner.client.deliveryservice.delivery.presentation.dto.response.GetDeliveryRouteResponse;
import com.winner.client.deliveryservice.delivery.presentation.dto.response.ListDeliveryRouteResponse;
import com.winner.client.global.exception.BusinessException;
import com.winner.client.global.pagination.CommonPageRequest;
import com.winner.client.global.pagination.PageResponse;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryQueryServiceImpl implements DeliveryQueryService {

  private final DeliveryRepository deliveryRepository;
  private final DeliveryRouteRepository deliveryRouteRepository;
  private final DeliveryCustomRepository deliveryCustomRepository;

  @Override
  public FindDeliveryResult getDeliveryDetail(UUID deliveryId) {
    Delivery delivery = deliveryRepository.findById(deliveryId)
        .orElseThrow(() -> new BusinessException(DeliveryErrorCode.NOT_FOUND_DELIVERY));
    return FindDeliveryResult.from(delivery);
  }

  @Override
  public List<FindDeliveryRouteResult> getDeliveryRoutes(UUID deliveryId) {
    validateDeliveryExists(deliveryId);
    List<DeliveryRoute> routes = deliveryRouteRepository.findAllByDeliveryId(deliveryId);

    return routes.stream().map(FindDeliveryRouteResult::from).toList();
  }

  @Override
  public Page<SearchDeliveryResult>  getDeliveryPage(SearchDeliveryQuery query) {
    Page<Delivery> deliveryPage = deliveryCustomRepository.getAllDeliveries(query);
    return deliveryPage.map(SearchDeliveryResult::from);
  }

  @Override
  public PageResponse<DeliveryRouteInfoResponse> getDeliveryRoutePage(UUID userId, UUID userRole,
      UUID referenceId) {
    return null;
  }

  @Override
  public FindDeliveryRouteResult getDeliveryRoute(UUID deliveryRouteId) {
    DeliveryRoute route = deliveryRouteRepository.findById(deliveryRouteId)
        .orElseThrow(() -> new BusinessException(DeliveryErrorCode.NOT_FOUND_DELIVERY_ROUTE));
    return FindDeliveryRouteResult.from(route);
  }


  private void validateDeliveryExists(UUID deliveryId) {
    deliveryRepository.findById(deliveryId)
        .orElseThrow(() -> new BusinessException(DeliveryErrorCode.NOT_FOUND_DELIVERY));
  }
}
