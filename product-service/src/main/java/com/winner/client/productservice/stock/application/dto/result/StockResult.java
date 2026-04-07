package com.winner.client.productservice.stock.application.dto.result;

import com.winner.client.productservice.stock.domain.entity.Stock;
import com.winner.client.productservice.stock.domain.vo.Quantity;
import java.time.LocalDateTime;
import java.util.UUID;

public record StockResult(
    UUID stockId,
    UUID productId,
    Quantity quantity,

    LocalDateTime createdAt,
    LocalDateTime updatedAt

) {
  public static StockResult from(Stock stock){
    return new StockResult(
        stock.getId(),
        stock.getProductId().getProductId(),
        stock.getQuantity(),
        stock.getCreatedAt(),
        stock.getUpdatedAt()
    );
  }
}
