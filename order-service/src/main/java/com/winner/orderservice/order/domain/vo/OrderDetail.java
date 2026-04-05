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
public class OrderDetail {

  @Column(name = "product_id", nullable = false, updatable = false)
  private UUID productId;

  @Column(name = "count", nullable = false)
  private Long count;

  @Column(name = "comment", columnDefinition = "text")
  private String comment;

  public OrderDetail(UUID productId, Long count, String comment) {
    validateNotNull(productId);
    validateCount(count);
    this.productId = productId;
    this.count = count;
    this.comment = comment;
  }

  public OrderDetail withCount(Long newCount) {
    validateCount(newCount);
    return new OrderDetail(this.productId, newCount, this.comment);
  }

  public OrderDetail withComment(String newComment) {
    return new OrderDetail(this.productId, this.count, newComment);
  }

  private void validateNotNull(UUID value) {
    if (value == null) {
      throw new BusinessException(OrderErrorCode.INVALID_INPUT);
    }
  }

  private void validateCount(Long count) {
    if (count == null) {
      throw new BusinessException(OrderErrorCode.INVALID_INPUT);
    }
    if (count < 1) {
      throw new BusinessException(OrderErrorCode.INVALID_INPUT);
    }
  }
}