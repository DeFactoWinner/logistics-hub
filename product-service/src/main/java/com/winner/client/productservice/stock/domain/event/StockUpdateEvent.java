package com.winner.client.productservice.stock.domain.event;

import com.winner.client.productservice.stock.domain.vo.ProductId;

public record StockUpdateEvent(
    ProductId productID,
    boolean isAvailable
) {
  public static StockUpdateEvent of(ProductId productID, boolean isAvailable) {
    return new StockUpdateEvent(productID, isAvailable);
  }
}
