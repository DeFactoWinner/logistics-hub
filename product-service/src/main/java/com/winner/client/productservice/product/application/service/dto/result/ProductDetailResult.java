package com.winner.client.productservice.product.application.service.dto.result;

import com.winner.client.productservice.product.domain.entity.Product;
import com.winner.client.productservice.product.domain.vo.StatusEnum;
import com.winner.client.productservice.stock.domain.entity.Stock;
import com.winner.client.productservice.stock.domain.vo.Quantity;
import java.time.LocalDateTime;
import java.util.UUID;

public record ProductDetailResult(
    UUID productId,
    String productName,
    String description,
    UUID hubId,
    UUID companyId,
    StatusEnum statusEnum,

    UUID stockId,
    int quantity,

    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

  public static ProductDetailResult of(Product product, Stock stock){
    return new ProductDetailResult(
        product.getId(),
        product.getName(),
        product.getDescription(),
        product.getHubId().getHubId(),
        product.getCompanyId().getCompanyId(),
        product.getStatusEnum(),
        stock.getId(),
        stock.getQuantity().getQuantity(),
        product.getCreatedAt(),
        product.getUpdatedAt()
    );
  }

}
