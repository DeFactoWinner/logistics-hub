package com.winner.orderservice.order.application.service.impl;

import com.winner.client.global.exception.BusinessException;
import com.winner.orderservice.common.UserContext;
import com.winner.orderservice.common.UserRole;
import com.winner.orderservice.order.application.service.OrderCommandService;
import com.winner.orderservice.order.application.dto.command.CreateOrderCommand;
import com.winner.orderservice.order.application.dto.command.UpdateOrderCommand;
import com.winner.orderservice.order.application.dto.result.OrderResult;
import com.winner.orderservice.order.domain.entity.Order;
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

    Order order = Order.create(
        new OrderParticipants(command.supplierId(), command.receiverId()),
        new OrderSnapshot(product.name(), command.deliveryAddress(), command.deliveryAddressDetail()),
        new OrderDetail(command.productId(), command.count(), command.comment()),
        product.hubId(),
        command.orderedAt()
    );
    orderRepository.save(order);


    try {
      var deliveryReq = new CreateDeliveryRequest(
          order.getId(),
          product.hubId(),
          command.receiverId(),
          command.deliveryAddress(),
          command.deliveryAddressDetail()
      );
      DeliveryResponse delivery = deliveryFeignClient.createDelivery(deliveryReq).getData();
      order.linkDelivery(delivery.deliveryId());
      order.confirm();
    } catch (Exception e) {
      log.error("배송 생성 실패 orderId={}", order.getId(), e);
      try {
        productFeignClient.modifyStock(command.productId(), new ModifyStockRequest(command.count()));
      } catch (Exception restoreEx) {
        log.error("보상 재고 복원 실패 — 수동 처리 필요 productId={}", command.productId(), restoreEx);
      }
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

    try {
      productFeignClient.modifyStock(productId, new ModifyStockRequest(count));
    } catch (Exception e) {
      log.error("재고 복원 실패 — 수동 처리 필요 orderId={}, productId={}", orderId, productId, e);
    }
    if (deliveryId != null) {
      try {
        deliveryFeignClient.cancelDelivery(deliveryId);
      } catch (Exception e) {
        log.error("배송 취소 실패 — 수동 처리 필요 orderId={}, deliveryId={}", orderId, deliveryId, e);
      }
    }
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

    try {
      productFeignClient.modifyStock(productId, new ModifyStockRequest(count));
    } catch (Exception e) {
      log.error("취소 재고 복원 실패 — 수동 처리 필요 orderId={}, productId={}", orderId, productId, e);
    }
    if (deliveryId != null) {
      try {
        deliveryFeignClient.cancelDelivery(deliveryId);
      } catch (Exception e) {
        log.error("배송 취소 실패 — 수동 처리 필요 orderId={}, deliveryId={}", orderId, deliveryId, e);
      }
    }

    return OrderResult.from(order);
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
