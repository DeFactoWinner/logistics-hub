package com.winner.client.productservice.product.application.service.dto.command;

import com.winner.client.productservice.product.presentation.dto.reqeust.UpdateProductRequest;
import java.util.UUID;

public record UpdateProductCommand(
    UUID productId,
    String name,
    String description
)
 {
  public static UpdateProductCommand of(UUID productId, UpdateProductRequest request) {
    return new UpdateProductCommand(
        productId,
        request.name(),
        request.description()
    );
  }
}
