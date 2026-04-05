package com.winner.orderservice.order.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.winner.client.global.exception.BusinessException;
import com.winner.orderservice.order.exception.OrderErrorCode;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderSnapshot {

  @Column(name = "product_name", nullable = false, updatable = false, length = 100)
  private String productName;

  @Column(name = "delivery_address", nullable = false, updatable = false, length = 255)
  private String deliveryAddress;

  @Column(name = "delivery_address_detail", nullable = false, updatable = false, length = 100)
  private String deliveryAddressDetail;

  public OrderSnapshot(String productName, String deliveryAddress, String deliveryAddressDetail) {
    validateNotEmpty(productName);
    validateNotEmpty(deliveryAddress);
    validateNotEmpty(deliveryAddressDetail);
    this.productName = productName;
    this.deliveryAddress = deliveryAddress;
    this.deliveryAddressDetail = deliveryAddressDetail;
  }

  private void validateNotEmpty(String value) {
    if (value == null || value.trim().isEmpty()) {
      throw new BusinessException(OrderErrorCode.INVALID_INPUT);
    }
  }
}