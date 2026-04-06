package com.winner.client.productservice.stock.domain.repository;

import com.winner.client.productservice.stock.domain.entity.Stock;
import com.winner.client.productservice.stock.domain.vo.ProductId;
import java.util.Optional;

public interface StockRepository {

  Stock save(Stock stock);

  Optional<Stock> findByProductIdAndDeletedAtIsNull(ProductId productId);


}
