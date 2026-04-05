package com.winner.client.productservice.stock.application.service;

import com.winner.client.productservice.stock.application.service.dto.command.CreateStockCommand;
import com.winner.client.productservice.stock.application.service.dto.command.DeleteStockCommand;
import com.winner.client.productservice.stock.application.service.dto.command.UpdateStockCommand;
import com.winner.client.productservice.stock.application.service.dto.result.StockResult;

public interface StockCommandService {

  void createStock(CreateStockCommand command);

  void deleteStock(DeleteStockCommand command);

  StockResult updateStock(UpdateStockCommand command);
}
