package com.winner.client.productservice.stock.domain.vo;

import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Quantity {

  @Column(name = "quantity", nullable = false)
  private int quantity;

  public Quantity(int quantity) {
    validateQuantity(quantity);
    this.quantity = quantity;
  }

  public Quantity add(int quantity) {

    int newQuantity = this.quantity + quantity;

    return new Quantity(newQuantity);
  }

  public Quantity reduce(int quantity) {

    int newQuantity = this.quantity - quantity;

    if(newQuantity < 0){
      throw new IllegalArgumentException("재고가 부족합니다.");
    }
    return new Quantity(newQuantity);
  }

  private void validateQuantity(int quantity){
    if(quantity < 0){
      throw new IllegalArgumentException("0 이상이어야 합니다.");
    }
  }


}
