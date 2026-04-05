package com.winner.orderservice.order.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.winner.client.global.exception.BusinessException;
import com.winner.orderservice.order.exception.OrderErrorCode;

import java.util.UUID;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderParticipants {

  @Column(name = "supplier_id", nullable = false, updatable = false)
  private UUID supplierId;

  @Column(name = "receiver_id", nullable = false, updatable = false)
  private UUID receiverId;

  public OrderParticipants(UUID supplierId, UUID receiverId) {
    validateNotNull(supplierId);
    validateNotNull(receiverId);
    this.supplierId = supplierId;
    this.receiverId = receiverId;
  }

  private void validateNotNull(UUID value) {
    if (value == null) {
      throw new BusinessException(OrderErrorCode.INVALID_INPUT);
    }
  }
}