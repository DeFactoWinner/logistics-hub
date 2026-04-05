package com.winner.client.deliveryservice.delivery.presentation.controller;

import com.winner.client.deliveryservice.delivery.application.service.DeliveryQueryService;
import com.winner.client.deliveryservice.delivery.application.service.DeliveryRouteCommandService;
import com.winner.client.deliveryservice.delivery.presentation.dto.request.UpdateDeliveryRequest;
import com.winner.client.deliveryservice.delivery.presentation.dto.response.DeliveryRouteCommandResponse;
import com.winner.client.deliveryservice.delivery.presentation.dto.response.GetDeliveryResponse;
import com.winner.client.deliveryservice.delivery.presentation.dto.response.GetDeliveryRouteResponse;
import com.winner.client.global.response.ApiResponse;
import com.winner.client.global.response.CommonSuccessCode;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/delivery-routes")
@RequiredArgsConstructor
public class DeliveryRouteController {
  private final DeliveryRouteCommandService deliveryRouteCommandService;
  private final DeliveryQueryService deliveryQueryService;

  @GetMapping("/{routeId}")
  public ResponseEntity<ApiResponse<GetDeliveryRouteResponse>> getDeliveryRoute(@PathVariable UUID routeId) {
    GetDeliveryRouteResponse response =
        GetDeliveryRouteResponse.from(deliveryQueryService.getDeliveryRoute(routeId));
    return ResponseEntity.status(CommonSuccessCode.OK.getStatus())
        .body(ApiResponse.success(CommonSuccessCode.OK, response));
  }

  @PatchMapping("/{routeId}")
  public ResponseEntity<ApiResponse<DeliveryRouteCommandResponse>> updateActualDeliveryRouteInfo(
      @PathVariable UUID routeId,
      @RequestBody UpdateDeliveryRequest request,
      @RequestHeader("X-User-Id") UUID userId
  ){
    DeliveryRouteCommandResponse response =
        deliveryRouteCommandService.updateActualDeliveryRouteInfo(routeId, request, userId);
    return ResponseEntity.status(CommonSuccessCode.OK.getStatus())
        .body(ApiResponse.success(CommonSuccessCode.OK, response));
  }

  @PatchMapping("{routeId}/in-progress")
  public ResponseEntity<ApiResponse<DeliveryRouteCommandResponse>> startProgress(
      @PathVariable UUID routeId,
      @RequestHeader("X-User-Id") UUID userId,
      @RequestHeader("X-User-Role") String userRole,
      @RequestHeader("X-Reference-Id") UUID referenceId
  ){
    DeliveryRouteCommandResponse response =
        deliveryRouteCommandService.startProgress(routeId, userId, userRole, referenceId);
    return ResponseEntity.status(CommonSuccessCode.OK.getStatus())
        .body(ApiResponse.success(CommonSuccessCode.OK, response));
  }

  @PatchMapping("{routeId}/completed")
  public ResponseEntity<ApiResponse<DeliveryRouteCommandResponse>> completeRoute(
      @PathVariable UUID routeId,
      @RequestHeader("X-User-Id") UUID userId,
      @RequestHeader("X-User-Role") String userRole,
      @RequestHeader("X-Reference-Id") UUID referenceId
  ){
    DeliveryRouteCommandResponse response =
        deliveryRouteCommandService.completeRoute(routeId, userId, userRole, referenceId);
    return ResponseEntity.status(CommonSuccessCode.OK.getStatus())
        .body(ApiResponse.success(CommonSuccessCode.OK, response));
  }

}
