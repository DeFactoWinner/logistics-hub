package com.winner.orderservice.order.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.winner.orderservice.order.application.service.OrderCommandService;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import com.winner.client.global.response.ApiResponse;
import com.winner.client.global.response.CommonSuccessCode;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/v1/orders")
public class InternalOrderController {

  private final OrderCommandService orderCommandService;

  @PostMapping("/{orderId}/assign-delivery-person")
  public ResponseEntity<ApiResponse<Void>> assignDeliveryPerson(
      @PathVariable UUID orderId,
      @RequestParam UUID assignedDeliveryPersonId) {
    orderCommandService.internalAssignDeliveryPerson(orderId, assignedDeliveryPersonId);
    return ResponseEntity.ok(ApiResponse.success(CommonSuccessCode.OK, null));
  }

  @PostMapping("/{orderId}/complete")
  public ResponseEntity<ApiResponse<Void>> completeOrder(@PathVariable UUID orderId) {
    orderCommandService.internalCompleteOrder(orderId);
    return ResponseEntity.ok(ApiResponse.success(CommonSuccessCode.OK, null));
  }

  @PostMapping("/{orderId}/cancel")
  public ResponseEntity<ApiResponse<Void>> cancelOrder(@PathVariable UUID orderId) {
    orderCommandService.internalCancelOrder(orderId);
    return ResponseEntity.ok(ApiResponse.success(CommonSuccessCode.OK, null));
  }
}
