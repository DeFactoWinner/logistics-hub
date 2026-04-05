package com.winner.client.productservice.product.application.service.dto.query;

import java.util.UUID;

public record FindProductDetailQuery(
    UUID productId
) {

  public static FindProductDetailQuery from(UUID productId) {
    return new FindProductDetailQuery(productId);
  }
}
