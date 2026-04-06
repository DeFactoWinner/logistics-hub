package com.winner.orderservice.order.application.service;

import com.winner.orderservice.common.UserContext;
import com.winner.orderservice.order.application.dto.command.CreateOrderCommand;
import com.winner.orderservice.order.application.dto.command.UpdateOrderCommand;
import com.winner.orderservice.order.application.dto.result.OrderResult;
import java.util.UUID;

public interface OrderCommandService {
  OrderResult createOrder(CreateOrderCommand command, UserContext ctx);
  OrderResult updateOrder(UUID orderId, UpdateOrderCommand command, UserContext ctx);
  void deleteOrder(UUID orderId, UserContext ctx);
  OrderResult confirmOrder(UUID orderId, UserContext ctx);
  OrderResult cancelOrder(UUID orderId, UserContext ctx);

  void internalAssignDeliveryPerson(UUID orderId, UUID deliveryPersonId);
  void internalCompleteOrder(UUID orderId);
  void internalCancelOrder(UUID orderId);
}