package com.winner.client.deliveryservice.delivery.application.service;

import com.winner.client.deliveryservice.delivery.presentation.dto.response.DeliveryInfoResponse;
import com.winner.client.deliveryservice.delivery.presentation.dto.response.DeliveryRouteInfoResponse;
import com.winner.client.deliveryservice.delivery.presentation.dto.response.GetDeliveryResponse;
import com.winner.client.deliveryservice.delivery.presentation.dto.response.GetDeliveryRouteResponse;
import com.winner.client.deliveryservice.delivery.presentation.dto.response.ListDeliveryRouteResponse;
import com.winner.client.global.pagination.CommonPageRequest;
import com.winner.client.global.pagination.PageResponse;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface DeliveryQueryService {
  GetDeliveryResponse getDeliveryDetail(UUID deliveryId);
  ListDeliveryRouteResponse getDeliveryRoutes(UUID deliveryId);
  PageResponse<DeliveryInfoResponse> getDeliveryPage(
      CommonPageRequest pageRequest, String keyword, String deliveryStatus,
      UUID userId, String userRole, UUID referenceId);
  PageResponse<DeliveryRouteInfoResponse> getDeliveryRoutePage(UUID userId, UUID userRole, UUID referenceId);
  GetDeliveryRouteResponse getDeliveryRoute(UUID deliveryRouteId);
}
