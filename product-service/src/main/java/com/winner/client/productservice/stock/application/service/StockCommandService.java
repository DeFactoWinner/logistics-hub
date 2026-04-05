package com.winner.client.productservice.stock.application.service;

import com.winner.client.productservice.stock.application.service.dto.command.CreateStockCommand;

public interface StockCommandService {

  void createStock(CreateStockCommand command);

}
