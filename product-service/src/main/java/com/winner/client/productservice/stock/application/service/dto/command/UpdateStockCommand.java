package com.winner.client.productservice.stock.application.service.dto.command;

import com.winner.client.productservice.stock.domain.vo.ProductId;
import com.winner.client.productservice.stock.presentation.dto.request.UpdateStockRequest;

public record UpdateStockCommand(
    int amount,
    ProductId productId
) {

  public static UpdateStockCommand of(ProductId productId, UpdateStockRequest request) {
    return new UpdateStockCommand(request.amount(),productId);
  }
}
