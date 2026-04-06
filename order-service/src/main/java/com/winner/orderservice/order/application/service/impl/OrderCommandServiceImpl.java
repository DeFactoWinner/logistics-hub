package com.winner.orderservice.order.application.service.impl;

import com.winner.client.global.exception.BusinessException;
import com.winner.orderservice.common.UserContext;
import com.winner.orderservice.common.UserRole;
import com.winner.orderservice.order.application.service.OrderCommandService;
import com.winner.orderservice.order.application.dto.command.CreateOrderCommand;
import com.winner.orderservice.order.application.dto.command.UpdateOrderCommand;
import com.winner.orderservice.order.application.dto.result.OrderResult;
import com.winner.orderservice.order.domain.entity.Order;
import com.winner.orderservice.order.domain.enums.OrderStatus;
import com.winner.orderservice.order.domain.vo.OrderDetail;
import com.winner.orderservice.order.domain.vo.OrderParticipants;
import com.winner.orderservice.order.domain.vo.OrderSnapshot;
import com.winner.orderservice.order.exception.OrderErrorCode;
import com.winner.orderservice.order.infrastructure.client.CompanyFeignClient;
import com.winner.orderservice.order.infrastructure.client.DeliveryFeignClient;
import com.winner.orderservice.order.infrastructure.client.ProductFeignClient;
import com.winner.orderservice.order.infrastructure.client.dto.response.CompanyResponse;
import com.winner.orderservice.order.infrastructure.client.dto.request.CreateDeliveryRequest;
import com.winner.orderservice.order.infrastructure.client.dto.response.DeliveryResponse;
import com.winner.orderservice.order.infrastructure.client.dto.request.ModifyStockRequest;
import com.winner.orderservice.order.infrastructure.client.dto.response.ProductResponse;
import com.winner.orderservice.order.infrastructure.repository.OrderRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OrderCommandServiceImpl implements OrderCommandService {

  private final OrderRepository orderRepository;
  private final ProductFeignClient productFeignClient;
  private final DeliveryFeignClient deliveryFeignClient;
  private final CompanyFeignClient companyFeignClient;

  @Override
  public OrderResult createOrder(CreateOrderCommand command, UserContext ctx) {
    requireRole(ctx, UserRole.MASTER, UserRole.HUB_MANAGER, UserRole.DELIVERY_MANAGER, UserRole.COMPANY_MANAGER);

    fetchCompany(command.supplierId());
    fetchCompany(command.receiverId());

    ProductResponse product = fetchProduct(command.productId());

    try {
      productFeignClient.modifyStock(command.productId(), new ModifyStockRequest(-command.count()));
    } catch (Exception e) {
      log.error("재고 감소 실패 productId={}", command.productId(), e);
      throw new BusinessException(OrderErrorCode.OUT_OF_STOCK);
    }

    Order order;
    try {
      order = Order.create(
          new OrderParticipants(command.supplierId(), command.receiverId()),
          new OrderSnapshot(product.name(), command.deliveryAddress(), command.deliveryAddressDetail()),
          new OrderDetail(command.productId(), command.count(), command.comment()),
          product.hubId(),
          command.orderedAt()
      );
      orderRepository.save(order);
    } catch (Exception e) {
      log.error("주문 생성/저장 실패 productId={}", command.productId(), e);
      restoreStock(command.productId(), command.count());
      throw new BusinessException(OrderErrorCode.ORDER_CREATE_FAILED);
    }

    try {
      var deliveryReq = new CreateDeliveryRequest(
          order.getId(),
          product.hubId(),
          command.receiverId(),
          command.deliveryAddress(),
          command.deliveryAddressDetail()
      );
      var deliveryResponse = deliveryFeignClient.createDelivery(deliveryReq);
      if (deliveryResponse == null || deliveryResponse.deliveryId() == null) {
        throw new BusinessException(OrderErrorCode.DELIVERY_CREATE_FAILED);
      }

      order.linkDelivery(deliveryResponse.deliveryId());
      order.confirm();
      if(deliveryResponse.deliveriesId() != null) {
        order.startShipping();
        order.assignDeliveryPerson(deliveryResponse.deliveriesId());
      }

    } catch (Exception e) {
      log.error("배송 생성 실패 orderId={}", order.getId(), e);
      restoreStock(command.productId(), command.count());
      throw new BusinessException(OrderErrorCode.DELIVERY_CREATE_FAILED);
    }

    return OrderResult.from(order);
  }

  @Override
  public OrderResult updateOrder(UUID orderId, UpdateOrderCommand command, UserContext ctx) {
    requireRole(ctx, UserRole.MASTER, UserRole.HUB_MANAGER);
    Order order = findActive(orderId);
    validateHubAccess(order, ctx);
    order.update(command.count(), command.comment());
    return OrderResult.from(order);
  }

  @Override
  public void deleteOrder(UUID orderId, UserContext ctx) {
    requireRole(ctx, UserRole.MASTER, UserRole.HUB_MANAGER);

    Order order = findActive(orderId);
    validateHubAccess(order, ctx);

    UUID productId = order.getOrderDetail().getProductId();
    Long count = order.getOrderDetail().getCount();
    UUID deliveryId = order.getDeliveryId();

    order.softDeleteOrder(ctx.getUserId());

    if (order.getStatus() == OrderStatus.CONFIRMED || order.getStatus() == OrderStatus.SHIPPING) {
      restoreStock(productId, count);
    }

    cancelDeliverySafely(deliveryId, ctx.getRole());
  }

  @Override
  public OrderResult confirmOrder(UUID orderId, UserContext ctx) {
    if (!ctx.hasRole(UserRole.MASTER, UserRole.HUB_MANAGER)) {
      throw new BusinessException(OrderErrorCode.INVALID_ROLE);
    }
    Order order = findActive(orderId);
    validateHubAccess(order, ctx);
    order.confirm();
    return OrderResult.from(order);
  }

  @Override
  public OrderResult cancelOrder(UUID orderId, UserContext ctx) {
    requireRole(ctx, UserRole.MASTER, UserRole.HUB_MANAGER, UserRole.COMPANY_MANAGER);

    Order order = findActive(orderId);
    validateCancelAccess(order, ctx);
    order.cancel();

    UUID productId = order.getOrderDetail().getProductId();
    Long count = order.getOrderDetail().getCount();
    UUID deliveryId = order.getDeliveryId();

    restoreStock(productId, count);
    cancelDeliverySafely(deliveryId, ctx.getRole());

    return OrderResult.from(order);
  }

  @Override
  public void internalAssignDeliveryPerson(UUID orderId, UUID deliveryPersonId) {
    Order order = findActive(orderId);
    order.assignDeliveryPerson(deliveryPersonId);
    if (order.getStatus() == OrderStatus.CONFIRMED) {
      order.startShipping();
    }
  }

  @Override
  public void internalCompleteOrder(UUID orderId) {
    Order order = findActive(orderId);
    order.complete();
  }

  @Override
  public void internalCancelOrder(UUID orderId) {
    Order order = findActive(orderId);
    order.cancel();

    UUID productId = order.getOrderDetail().getProductId();
    Long count = order.getOrderDetail().getCount();
    UUID deliveryId = order.getDeliveryId();

    restoreStock(productId, count);
    cancelDeliverySafely(deliveryId, UserRole.MASTER);
  }

  private void restoreStock(UUID productId, Long count) {
    try {
      productFeignClient.modifyStock(productId, new ModifyStockRequest(count));
    } catch (Exception e) {
      log.error("보상 재고 복원 실패 — 수동 처리 필요 productId={}", productId, e);
    }
  }

  private void cancelDeliverySafely(UUID deliveryId, UserRole userRole) {
    if (deliveryId == null) return;
    try {
      deliveryFeignClient.cancelDelivery(deliveryId,userRole.name());
    } catch (Exception e) {
      log.error("배송 취소 실패 — 수동 처리 필요 deliveryId={}", deliveryId, e);
    }
  }

  private Order findActive(UUID orderId) {
    return orderRepository.findActiveById(orderId)
        .orElseThrow(() -> new BusinessException(OrderErrorCode.ORDER_NOT_FOUND));
  }

  private ProductResponse fetchProduct(UUID productId) {
    try {
      var response = productFeignClient.getProduct(productId);
      if (response == null || response.getData() == null) {
        throw new BusinessException(OrderErrorCode.PRODUCT_NOT_FOUND);
      }
      return response.getData();
    } catch (BusinessException e) {
      throw e;
    } catch (Exception e) {
      throw new BusinessException(OrderErrorCode.PRODUCT_NOT_FOUND);
    }
  }

  private CompanyResponse fetchCompany(UUID companyId) {
    try {
      var response = companyFeignClient.getCompany(companyId);
      if (response == null || response.getData() == null) {
        throw new BusinessException(OrderErrorCode.COMPANY_NOT_FOUND);
      }
      return response.getData();
    } catch (BusinessException e) {
      throw e;
    } catch (Exception e) {
      throw new BusinessException(OrderErrorCode.COMPANY_NOT_FOUND);
    }
  }

  private void requireRole(UserContext ctx, UserRole... roles) {
    if (!ctx.hasRole(roles)) {
      throw new BusinessException(OrderErrorCode.ACCESS_DENIED);
    }
  }

  private void validateHubAccess(Order order, UserContext ctx) {
    if (ctx.isMaster()) return;
    if (ctx.isHub() && order.getHubId().equals(ctx.getHubId())) return;
    throw new BusinessException(OrderErrorCode.ACCESS_DENIED);
  }

  private void validateCancelAccess(Order order, UserContext ctx) {
    if (ctx.isMaster()) return;
    if (ctx.isHub() && order.getHubId().equals(ctx.getHubId())) return;
    if (ctx.isCompany()) {
      UUID sid = ctx.getCompanyId();
      if (order.getParticipants().getSupplierId().equals(sid) ||
          order.getParticipants().getReceiverId().equals(sid)) return;
    }
    throw new BusinessException(OrderErrorCode.ACCESS_DENIED);
  }
}
