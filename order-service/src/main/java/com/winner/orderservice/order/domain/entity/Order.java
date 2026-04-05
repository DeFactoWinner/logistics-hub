package com.winner.orderservice.order.domain.entity;

import com.winner.client.global.entity.BaseAuditEntity;
import com.winner.client.global.exception.BusinessException;
import com.winner.orderservice.order.domain.enums.OrderStatus;
import com.winner.orderservice.order.domain.vo.OrderDetail;
import com.winner.orderservice.order.domain.vo.OrderParticipants;
import com.winner.orderservice.order.domain.vo.OrderSnapshot;
import com.winner.orderservice.order.exception.OrderErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseAuditEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id;

  @Embedded
  private OrderParticipants participants;

  @Embedded
  private OrderSnapshot snapshot;

  @Embedded
  private OrderDetail orderDetail;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private OrderStatus status;

  @Column(name = "delivery_id")
  private UUID deliveryId;

  @Column(name = "assigned_delivery_person_id")
  private UUID assignedDeliveryPersonId;

  @Column(name = "hub_id", nullable = false, updatable = false)
  private UUID hubId;

  @Column(name = "ordered_at", nullable = false, updatable = false)
  private LocalDateTime orderedAt;

  public static Order create(
      OrderParticipants participants,
      OrderSnapshot snapshot,
      OrderDetail orderDetail,
      UUID hubId,
      LocalDateTime orderedAt
  ) {
    validateNotNull(participants);
    validateNotNull(snapshot);
    validateNotNull(orderDetail);
    validateNotNull(hubId);
    validateNotNull(orderedAt);

    Order order = new Order();
    order.participants = participants;
    order.snapshot = snapshot;
    order.orderDetail = orderDetail;
    order.hubId = hubId;
    order.status = OrderStatus.PENDING;
    order.orderedAt = orderedAt;
    return order;
  }

  private static void validateNotNull(Object value) {
    if (value == null) {
      throw new BusinessException(OrderErrorCode.INVALID_INPUT);
    }
  }

  public void update(Long count, String comment) {
    if (!status.isEditable()) {
      throw new BusinessException(OrderErrorCode.INVALID_ORDER_STATUS);
    }
    if (count != null) {
      this.orderDetail = this.orderDetail.withCount(count);
    }
    if (comment != null) {
      this.orderDetail = this.orderDetail.withComment(comment);
    }
  }

  public void confirm() {
    transitionTo(OrderStatus.CONFIRMED);
  }

  public void cancel() {
    if (!status.isCancellable()) {
      throw new BusinessException(OrderErrorCode.INVALID_ORDER_STATUS_TRANSITION);
    }
    this.status = OrderStatus.CANCELLED;
  }

  public void startShipping() {
    transitionTo(OrderStatus.SHIPPING);
  }

  public void complete() {
    transitionTo(OrderStatus.DELIVERED);
  }

  public void linkDelivery(UUID deliveryId) {
    this.deliveryId = deliveryId;
  }

  public void assignDeliveryPerson(UUID deliveryPersonId) {
    this.assignedDeliveryPersonId = deliveryPersonId;
  }

  public void softDeleteOrder(UUID userId) {
    if (!status.isDeletable()) {
      throw new BusinessException(OrderErrorCode.INVALID_ORDER_STATUS);
    }
    super.softDelete(userId);
  }

  private void transitionTo(OrderStatus next) {
    if (!status.canTransitionTo(next)) {
      throw new BusinessException(OrderErrorCode.INVALID_ORDER_STATUS_TRANSITION);
    }
    this.status = next;
  }
}