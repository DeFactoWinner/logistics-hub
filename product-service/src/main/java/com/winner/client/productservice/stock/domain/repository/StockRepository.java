package com.winner.client.productservice.stock.domain.repository;

import com.winner.client.productservice.stock.domain.entity.Stock;
import com.winner.client.productservice.stock.domain.vo.ProductId;
import java.util.List;
import java.util.UUID;

public interface StockRepository {

  Stock save(Stock stock);

  Stock findByProductIdAndDeletedAtIsNull(ProductId productId);

  List<Stock> findAllByDeletedAtIsNull();
}
