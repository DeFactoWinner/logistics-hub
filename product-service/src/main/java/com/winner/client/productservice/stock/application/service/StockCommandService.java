package com.winner.client.productservice.stock.application.service;

import com.winner.client.global.security.CustomUserPrincipal;
import com.winner.client.productservice.stock.application.dto.command.CreateStockCommand;
import com.winner.client.productservice.stock.application.dto.command.DeleteStockCommand;
import com.winner.client.productservice.stock.application.dto.command.UpdateStockCommand;
import com.winner.client.productservice.stock.application.dto.result.StockResult;

public interface StockCommandService {

  void createStock(CreateStockCommand command);

  void deleteStock(DeleteStockCommand command);

  StockResult updateStock(UpdateStockCommand command, CustomUserPrincipal userPrincipal);
}
