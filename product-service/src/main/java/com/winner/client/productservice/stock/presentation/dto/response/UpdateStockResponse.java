package com.winner.client.productservice.stock.presentation.dto.response;

import com.winner.client.productservice.stock.application.dto.result.StockResult;
import java.util.UUID;

public record UpdateStockResponse(
    UUID productId,

    int totalAmount
) {
  public static UpdateStockResponse from(StockResult result) {
    return new UpdateStockResponse(
        result.productId(),
        result.quantity().getQuantity()
    );
  }
}
