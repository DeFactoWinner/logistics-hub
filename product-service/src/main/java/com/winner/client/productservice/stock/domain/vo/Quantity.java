package com.winner.client.productservice.stock.domain.vo;

import com.winner.client.global.exception.BusinessException;
import com.winner.client.productservice.common.exception.StockErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Quantity {

  @Column(name = "quantity", nullable = false)
  private int quantity;

  public Quantity(int quantity) {
    validateQuantity(quantity);
    this.quantity = quantity;
  }

  public Quantity add(int delta) {
    int newQuantity = this.quantity + delta;

    if (newQuantity < 0) {
      throw new BusinessException(StockErrorCode.INSUFFICIENT_STOCK);
    }

    return new Quantity(newQuantity);
  }

  private void validateQuantity(int quantity){
    if(quantity < 0){
      throw new BusinessException(StockErrorCode.INVALID_STOCK_QUANTITY);
    }
  }


}
