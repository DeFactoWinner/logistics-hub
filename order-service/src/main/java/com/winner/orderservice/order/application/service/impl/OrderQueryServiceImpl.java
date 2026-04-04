package com.winner.orderservice.order.application.service.impl;

import com.winner.client.global.exception.BusinessException;
import com.winner.orderservice.common.UserContext;
import com.winner.orderservice.common.UserRole;
import com.winner.orderservice.order.application.service.OrderQueryService;
import com.winner.orderservice.order.application.dto.command.SearchOrderCommand;
import com.winner.orderservice.order.application.dto.result.OrderResult;
import com.winner.orderservice.order.application.dto.result.OrderSummaryResult;
import com.winner.orderservice.order.domain.entity.Order;
import com.winner.orderservice.order.exception.OrderErrorCode;
import com.winner.orderservice.order.infrastructure.repository.OrderRepository;
import com.winner.orderservice.order.infrastructure.repository.OrderSpecification;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderQueryServiceImpl implements OrderQueryService {

  private final OrderRepository orderRepository;

  @Override
  public OrderResult getOrder(UUID orderId, UserContext ctx) {
    requireRole(ctx, UserRole.MASTER, UserRole.HUB_MANAGER, UserRole.DELIVERY_MANAGER, UserRole.COMPANY_MANAGER);
    Order order = findActive(orderId);
    validateReadAccess(order, ctx);
    return OrderResult.from(order);
  }

  @Override
  public Page<OrderSummaryResult> getOrders(SearchOrderCommand command, Pageable pageable, UserContext ctx) {
    requireRole(ctx, UserRole.MASTER, UserRole.HUB_MANAGER, UserRole.DELIVERY_MANAGER, UserRole.COMPANY_MANAGER);

    SearchOrderCommand scopedCommand = applyScopeCondition(command, ctx);
    Pageable normalizedPageable = OrderSpecification.toPageable(pageable, scopedCommand);
    Specification<Order> spec = OrderSpecification.of(scopedCommand);
    return orderRepository.findAll(spec, normalizedPageable).map(OrderSummaryResult::from);
  }

  private Order findActive(UUID orderId) {
    return orderRepository.findActiveById(orderId)
        .orElseThrow(() -> new BusinessException(OrderErrorCode.ORDER_NOT_FOUND));
  }

  private void requireRole(UserContext ctx, UserRole... roles) {
    if (!ctx.hasRole(roles)) {
      throw new BusinessException(OrderErrorCode.ACCESS_DENIED);
    }
  }

  private void validateReadAccess(Order order, UserContext ctx) {
    if (ctx.isMaster()) return;
    if (ctx.isHub() && order.getHubId().equals(ctx.getHubId())) return;
    if (ctx.isCompany()) {
      UUID sid = ctx.getCompanyId();
      if (order.getParticipants().getSupplierId().equals(sid) ||
          order.getParticipants().getReceiverId().equals(sid)) return;
    }
    if (ctx.isDelivery() && order.getAssignedDeliveryPersonId() != null &&
        order.getAssignedDeliveryPersonId().equals(ctx.getUserId())) return;
    throw new BusinessException(OrderErrorCode.ACCESS_DENIED);
  }

  private SearchOrderCommand applyScopeCondition(SearchOrderCommand cond, UserContext ctx) {
    if (ctx.isMaster()) return cond;

    UUID hubId = ctx.isHub() ? ctx.getHubId() : cond.hubId();
    UUID companyId = ctx.isCompany() ? ctx.getCompanyId() : cond.companyId();
    UUID deliveryId = cond.deliveryId();
    UUID assignedDeliveryPersonId = ctx.isDelivery() ? ctx.getUserId() : cond.assignedDeliveryPersonId();

    return new SearchOrderCommand(
        cond.status(),
        cond.from(),
        cond.to(),
        hubId,
        companyId,
        deliveryId,
        assignedDeliveryPersonId,
        cond.sortBy(),
        cond.sortDirection()
    );
  }
}
