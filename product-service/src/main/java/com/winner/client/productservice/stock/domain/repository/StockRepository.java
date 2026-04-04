package com.winner.client.productservice.stock.domain.repository;

import com.winner.client.productservice.stock.domain.entity.Stock;

public interface StockRepository {

  Stock save(Stock stock);

}
