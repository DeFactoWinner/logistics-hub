package com.winner.client.productservice.product.domain.repository;

import com.winner.client.productservice.product.domain.entity.Product;
import com.winner.client.productservice.stock.domain.entity.Stock;
import lombok.Getter;

@Getter
public class ProductStockProjection {
  private final Product product;
  private final Stock stock;

  public ProductStockProjection(Product product, Stock stock) {
    this.product = product;
    this.stock = stock;
  }
}