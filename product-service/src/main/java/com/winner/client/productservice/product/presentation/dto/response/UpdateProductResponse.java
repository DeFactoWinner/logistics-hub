package com.winner.client.productservice.product.presentation.dto.response;

import com.winner.client.productservice.product.application.service.dto.result.ProductResult;
import java.util.UUID;

public record UpdateProductResponse(
    UUID productId,
    String name,
    String description
) {

  public static UpdateProductResponse from(ProductResult result){
    return new UpdateProductResponse(
        result.productId(),
        result.productName(),
        result.description()
    );
  }
}
