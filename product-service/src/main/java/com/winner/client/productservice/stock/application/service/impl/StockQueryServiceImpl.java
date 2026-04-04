package com.winner.client.productservice.stock.application.service.impl;

import com.winner.client.productservice.stock.application.service.StockQueryService;
import com.winner.client.productservice.stock.application.service.dto.result.StockResult;
import com.winner.client.productservice.stock.domain.entity.Stock;
import com.winner.client.productservice.stock.domain.repository.StockRepository;
import com.winner.client.productservice.stock.domain.vo.ProductId;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockQueryServiceImpl implements StockQueryService {

  private final StockRepository stockRepository;

  @Override
  public StockResult getStock(UUID productId) {

    Stock stock = stockRepository.findByProductIdAndDeletedAtIsNull(ProductId.of(productId));

    return StockResult.from(stock);
  }

  @Override
  public List<StockResult> getStocks() {
    List<Stock> stocks = stockRepository.findAllByDeletedAtIsNull();
    return stocks.stream().
        map(StockResult::from).
        toList();
  }
}
