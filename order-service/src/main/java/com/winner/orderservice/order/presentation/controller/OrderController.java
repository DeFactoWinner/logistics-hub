package com.winner.orderservice.order.presentation.controller;

import com.winner.client.global.response.ApiResponse;
import com.winner.client.global.response.CommonSuccessCode;
import com.winner.orderservice.common.UserContext;
import com.winner.orderservice.order.presentation.dto.request.CreateOrderRequest;
import com.winner.orderservice.order.presentation.dto.request.OrderSearchCondition;
import com.winner.orderservice.order.presentation.dto.request.UpdateOrderRequest;
import com.winner.orderservice.order.presentation.dto.response.OrderResponse;
import com.winner.orderservice.order.presentation.dto.response.OrderSummaryResponse;
import com.winner.orderservice.order.application.service.OrderCommandService;
import com.winner.orderservice.order.application.service.OrderQueryService;
import com.winner.orderservice.order.application.dto.command.CreateOrderCommand;
import com.winner.orderservice.order.application.dto.command.SearchOrderCommand;
import com.winner.orderservice.order.application.dto.command.UpdateOrderCommand;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

  private final OrderCommandService orderCommandService;
  private final OrderQueryService orderQueryService;

  @PostMapping
  public ResponseEntity<ApiResponse<OrderResponse>> createOrder(
      @Valid @RequestBody CreateOrderRequest request,
      UserContext ctx
  ) {
    return ResponseEntity
        .status(CommonSuccessCode.CREATED.getStatus())
        .body(ApiResponse.success(CommonSuccessCode.CREATED,
            OrderResponse.fromResult(orderCommandService.createOrder(CreateOrderCommand.from(request), ctx))));
  }

  @GetMapping("/{orderId}")
  public ResponseEntity<ApiResponse<OrderResponse>> getOrder(
      @PathVariable UUID orderId,
      UserContext ctx
  ) {
    return ResponseEntity.ok(ApiResponse.success(CommonSuccessCode.OK,
        OrderResponse.fromResult(orderQueryService.getOrder(orderId, ctx))));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<Page<OrderSummaryResponse>>> getOrders(
      @ParameterObject OrderSearchCondition condition,
      @PageableDefault(size = 10) Pageable pageable,
      UserContext ctx
  ) {
    return ResponseEntity.ok(ApiResponse.success(CommonSuccessCode.OK,
        orderQueryService.getOrders(SearchOrderCommand.from(condition), pageable, ctx).map(OrderSummaryResponse::fromResult)));
  }

  @PatchMapping("/{orderId}")
  public ResponseEntity<ApiResponse<OrderResponse>> updateOrder(
      @PathVariable UUID orderId,
      @Valid @RequestBody UpdateOrderRequest request,
      UserContext ctx
  ) {
    return ResponseEntity.ok(ApiResponse.success(CommonSuccessCode.OK,
        OrderResponse.fromResult(orderCommandService.updateOrder(orderId, UpdateOrderCommand.from(request), ctx))));
  }

  @DeleteMapping("/{orderId}")
  public ResponseEntity<ApiResponse<Void>> deleteOrder(
      @PathVariable UUID orderId,
      UserContext ctx
  ) {
    orderCommandService.deleteOrder(orderId, ctx);
    return ResponseEntity.ok(ApiResponse.success(CommonSuccessCode.DELETED, null));
  }

  @PostMapping("/{orderId}/confirmations")
  public ResponseEntity<ApiResponse<OrderResponse>> confirmOrder(
      @PathVariable UUID orderId,
      UserContext ctx
  ) {
    return ResponseEntity.ok(ApiResponse.success(CommonSuccessCode.OK,
        OrderResponse.fromResult(orderCommandService.confirmOrder(orderId, ctx))));
  }

  @PostMapping("/{orderId}/cancellations")
  public ResponseEntity<ApiResponse<OrderResponse>> cancelOrder(
      @PathVariable UUID orderId,
      UserContext ctx
  ) {
    return ResponseEntity.ok(ApiResponse.success(CommonSuccessCode.OK,
        OrderResponse.fromResult(orderCommandService.cancelOrder(orderId, ctx))));
  }
}
