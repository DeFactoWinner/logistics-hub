package com.winner.client.deliveryservice.delivery.presentation.controller;

import com.winner.client.deliveryservice.delivery.application.dto.query.SearchDeliveryQuery;
import com.winner.client.deliveryservice.delivery.application.dto.result.SearchDeliveryResult;
import com.winner.client.deliveryservice.delivery.application.service.DeliveryCommandService;
import com.winner.client.deliveryservice.delivery.application.service.DeliveryQueryService;
import com.winner.client.deliveryservice.delivery.presentation.dto.response.DeliveryCommandResponse;
import com.winner.client.deliveryservice.delivery.presentation.dto.response.DeliveryInfoResponse;
import com.winner.client.deliveryservice.delivery.presentation.dto.response.GetDeliveryResponse;
import com.winner.client.deliveryservice.delivery.presentation.dto.response.ListDeliveryRouteResponse;
import com.winner.client.global.pagination.CommonPageRequest;
import com.winner.client.global.pagination.PageResponse;
import com.winner.client.global.response.ApiResponse;
import com.winner.client.global.response.CommonSuccessCode;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/deliveries")
@RequiredArgsConstructor
public class DeliveryController {
  private final DeliveryQueryService deliveryQueryService;
  private final DeliveryCommandService deliveryCommandService;

  @GetMapping
  public ResponseEntity<ApiResponse<PageResponse<DeliveryInfoResponse>>> getMyDeliveries(
      CommonPageRequest pageRequest,
      @RequestParam(value = "keyword", required = false) String keyword,
      @RequestParam(value = "delivery-status", required = false) String deliveryStatus,
      @RequestHeader("X-User-Id") UUID userId,
      @RequestHeader("X-User-Role") String userRole,
      @RequestHeader("X-Reference-Id") UUID referenceId
  ) {
    SearchDeliveryQuery query =
        SearchDeliveryQuery.of(userId, userRole, referenceId, keyword, deliveryStatus, pageRequest);

    Page<SearchDeliveryResult> resultPage = deliveryQueryService.getDeliveryPage(query);
    Page<DeliveryInfoResponse> infoPage = resultPage.map(DeliveryInfoResponse::from);
    PageResponse<DeliveryInfoResponse> response = PageResponse.of(infoPage);
    return ResponseEntity.status(CommonSuccessCode.OK.getStatus())
        .body(ApiResponse.success(CommonSuccessCode.OK, response));
  }

  @GetMapping("/{deliveryId}")
  public ResponseEntity<ApiResponse<GetDeliveryResponse>> getDeliveryDetail(@PathVariable UUID deliveryId) {
    GetDeliveryResponse response =
        GetDeliveryResponse.from(deliveryQueryService.getDeliveryDetail(deliveryId));
    return ResponseEntity.status(CommonSuccessCode.OK.getStatus())
        .body(ApiResponse.success(CommonSuccessCode.OK, response));
  }

  @GetMapping("/{deliveryId}/routes")
  public ResponseEntity<ApiResponse<ListDeliveryRouteResponse>> getDeliveryRoutes(@PathVariable UUID deliveryId){
    ListDeliveryRouteResponse response =
        ListDeliveryRouteResponse.from(deliveryQueryService.getDeliveryRoutes(deliveryId));
    return ResponseEntity.status(CommonSuccessCode.OK.getStatus())
        .body(ApiResponse.success(CommonSuccessCode.OK, response));
  }

  @PatchMapping("/{deliveryId}/hub-moving")
  public ResponseEntity<ApiResponse<DeliveryCommandResponse>> startHubMoving(
      @PathVariable UUID deliveryId,
      @RequestHeader("X-User-Role") String userRole,
      @RequestHeader("X-Reference-Id") UUID referenceId
  ){
    DeliveryCommandResponse response =
        deliveryCommandService.startHubMoving(deliveryId, userRole, referenceId);
    return ResponseEntity.status(CommonSuccessCode.OK.getStatus())
        .body(ApiResponse.success(CommonSuccessCode.OK, response));
  }

  @PatchMapping("/{deliveryId}/destination-arrived")
  public ResponseEntity<ApiResponse<DeliveryCommandResponse>> startVendorMoving(
      @PathVariable UUID deliveryId,
      @RequestHeader("X-User-Id") UUID userId,
      @RequestHeader("X-User-Role") String userRole,
      @RequestHeader("X-Reference-Id") UUID referenceId
  ){
    DeliveryCommandResponse response
        = deliveryCommandService.startVendorMoving(deliveryId, userId, userRole, referenceId);
    return ResponseEntity.status(CommonSuccessCode.OK.getStatus())
        .body(ApiResponse.success(CommonSuccessCode.OK, response));
  }

  @PatchMapping("/{deliveryId}/for-vendor-moving")
  public ResponseEntity<ApiResponse<DeliveryCommandResponse>> arriveDestination(
      @PathVariable UUID deliveryId,
      @RequestHeader("X-User-Role") String userRole,
      @RequestHeader("X-Reference-Id") UUID referenceId
  ){
    DeliveryCommandResponse response =
        deliveryCommandService.arriveDestination(deliveryId, userRole, referenceId);
    return ResponseEntity.status(CommonSuccessCode.OK.getStatus())
        .body(ApiResponse.success(CommonSuccessCode.OK, response));
  }

  @PatchMapping("/{deliveryId}/completed")
  public ResponseEntity<ApiResponse<DeliveryCommandResponse>> completeDelivery(
      @PathVariable UUID deliveryId,
      @RequestHeader("X-User-Id") UUID userId,
      @RequestHeader("X-User-Role") String userRole,
      @RequestHeader("X-Reference-Id") UUID referenceId
  ){
    DeliveryCommandResponse response =
        deliveryCommandService.completeDelivery(deliveryId, userId, userRole, referenceId);
    return ResponseEntity.status(CommonSuccessCode.OK.getStatus())
        .body(ApiResponse.success(CommonSuccessCode.OK, response));
  }

  @PatchMapping("/{deliveryId}/cancelled")
  public ResponseEntity<ApiResponse<DeliveryCommandResponse>> cancelDelivery(
      @PathVariable UUID deliveryId,
      @RequestHeader("X-User-Role") String userRole
  ){
    DeliveryCommandResponse response
        = deliveryCommandService.cancelDelivery(deliveryId, userRole);
    return ResponseEntity.status(CommonSuccessCode.OK.getStatus())
        .body(ApiResponse.success(CommonSuccessCode.OK, response));
  }
}
