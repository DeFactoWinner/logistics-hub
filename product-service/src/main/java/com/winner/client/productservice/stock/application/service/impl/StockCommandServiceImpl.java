package com.winner.client.productservice.stock.application.service.impl;

import com.winner.client.productservice.stock.application.service.StockCommandService;
import com.winner.client.productservice.stock.application.service.dto.command.CreateStockCommand;
import com.winner.client.productservice.stock.domain.entity.Stock;
import com.winner.client.productservice.stock.domain.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class StockCommandServiceImpl implements StockCommandService {

  private final StockRepository stockRepository;

  @Override
  public void createStock(CreateStockCommand command) {

    Stock stock = Stock.create(command.productId());
    stockRepository.save(stock);
  }
}
