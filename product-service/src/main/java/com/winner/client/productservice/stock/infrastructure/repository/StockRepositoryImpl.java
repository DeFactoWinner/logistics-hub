package com.winner.client.productservice.stock.infrastructure.repository;

import com.winner.client.productservice.stock.domain.entity.Stock;
import com.winner.client.productservice.stock.domain.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StockRepositoryImpl implements StockRepository {

  private final StockJpaRepository stockJpaRepository;

  @Override
  public Stock save(Stock stock) {
    return stockJpaRepository.save(stock);
  }
}
