package com.winner.client.productservice.stock.application.service.impl;

import com.winner.client.productservice.stock.application.service.StockCommandService;
import com.winner.client.productservice.stock.application.service.dto.command.CreateStockCommand;
import com.winner.client.productservice.stock.domain.entity.Stock;
import com.winner.client.productservice.stock.domain.repository.StockRepository;
import com.winner.client.productservice.stock.infrastructure.repository.StockRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StockCommandServiceImpl implements StockCommandService {

  private final StockRepository stockRepository;

  @Override
  public void createStock(CreateStockCommand command) {

    Stock stock = Stock.create(command.productId());
    stockRepository.save(stock);
  }
}
