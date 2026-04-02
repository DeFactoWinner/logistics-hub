package com.winner.client.productservice.product.domain.vo;

public enum StatusEnum {
  INACTIVE,
  ON_SALE,
  SOLD_OUT;

  public boolean isOnStock() {
    return this == ON_SALE;
  }

  public boolean isOutOfStock() {
    return this == SOLD_OUT;
  }
}
