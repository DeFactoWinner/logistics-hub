package com.winner.client.productservice.product.application.service.dto.result;

import com.winner.client.productservice.product.domain.entity.Product;
import com.winner.client.productservice.product.domain.vo.StatusEnum;
import java.util.UUID;

public record ProductResult(
    UUID productId,
    String productName,
    String description,
    UUID hubId,
    UUID companyId,
    StatusEnum statusEnum
) {

  public static ProductResult from(Product product){
    return new ProductResult(
        product.getId(),
        product.getName(),
        product.getDescription(),
        product.getHubId().getHubId(),
        product.getCompanyId().getCompanyId(),
        product.getStatusEnum()
    );
  }
}
