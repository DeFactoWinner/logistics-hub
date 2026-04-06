package com.winner.client.productservice.stock.application.service.impl;

import com.winner.client.global.exception.BusinessException;
import com.winner.client.productservice.common.exception.StockErrorCode;
import com.winner.client.productservice.stock.application.service.StockQueryService;
import com.winner.client.productservice.stock.application.dto.result.StockResult;
import com.winner.client.productservice.stock.domain.entity.Stock;
import com.winner.client.productservice.stock.domain.repository.StockRepository;
import com.winner.client.productservice.stock.domain.vo.ProductId;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockQueryServiceImpl implements StockQueryService {

  private final StockRepository stockRepository;

  @Override
  public StockResult getStock(UUID productId) {
    Stock stock = stockRepository.findByProductIdAndDeletedAtIsNull(ProductId.of(productId))
        .orElseThrow(() -> new BusinessException(StockErrorCode.STOCK_NOT_FOUND));
    return StockResult.from(stock);
  }

}
