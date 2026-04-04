package com.winner.client.productservice.product.presentation.dto.response;

import com.winner.client.productservice.product.application.service.dto.result.ProductResult;
import com.winner.client.productservice.product.domain.vo.StatusEnum;
import java.util.UUID;

public record CreateProductResponse(
    String productName,
    UUID hubId,
    UUID companyId,
    StatusEnum statusEnum,
    String description
) {

  public static CreateProductResponse from(ProductResult result) {
    return new CreateProductResponse(
        result.productName(),
        result.hubId(),
        result.companyId(),
        result.statusEnum(),
        result.description());
  }
}
