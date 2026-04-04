package com.winner.client.productservice.stock.infrastructure.repository;

import com.winner.client.global.code.ErrorCode;
import com.winner.client.global.exception.BusinessException;
import com.winner.client.global.exception.CommonErrorCode;
import com.winner.client.productservice.common.exception.ProductErrorCode;
import com.winner.client.productservice.stock.domain.entity.Stock;
import com.winner.client.productservice.stock.domain.repository.StockRepository;
import com.winner.client.productservice.stock.domain.vo.ProductId;
import java.util.List;
import java.util.UUID;
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

  @Override
  public Stock findByProductIdAndDeletedAtIsNull(ProductId productId) {
    return stockJpaRepository.findByProductIdAndDeletedAtIsNull(productId);
  }

  @Override
  public List<Stock> findAllByDeletedAtIsNull() {
    return List.of();
  }

}
