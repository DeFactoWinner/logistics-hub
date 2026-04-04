package com.winner.orderservice.order.application.service;

import com.winner.orderservice.common.UserContext;
import com.winner.orderservice.order.application.dto.command.SearchOrderCommand;
import com.winner.orderservice.order.application.dto.result.OrderResult;
import com.winner.orderservice.order.application.dto.result.OrderSummaryResult;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderQueryService {
  OrderResult getOrder(UUID orderId, UserContext ctx);
  Page<OrderSummaryResult> getOrders(SearchOrderCommand command, Pageable pageable, UserContext ctx);
}

