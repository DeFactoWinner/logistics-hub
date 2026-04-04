package com.winner.client.productservice.stock.application.service;

import com.winner.client.productservice.stock.application.service.dto.result.StockResult;
import java.util.List;
import java.util.UUID;

public interface StockQueryService {

  StockResult getStock(UUID productId);

  List<StockResult> getStocks();
}
