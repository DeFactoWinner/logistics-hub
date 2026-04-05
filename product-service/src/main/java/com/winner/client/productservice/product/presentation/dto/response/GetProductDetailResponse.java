package com.winner.client.productservice.product.presentation.dto.response;

import com.winner.client.productservice.product.application.service.dto.result.ProductDetailResult;
import com.winner.client.productservice.product.domain.vo.StatusEnum;
import java.time.LocalDateTime;
import java.util.UUID;

public record GetProductDetailResponse(
    UUID productId,
    UUID hubId,
    UUID companyId,
    UUID stockId,

    String name,
    String description,

    StatusEnum statusEnum,

    int quantity,

    LocalDateTime createdAt,
    LocalDateTime updatedAt

) {
  public static GetProductDetailResponse from(ProductDetailResult result){
    return new GetProductDetailResponse(
        result.productId(),
        result.hubId(),
        result.companyId(),
        result.stockId(),
        result.productName(),
        result.description(),
        result.statusEnum(),
        result.quantity(),
        result.createdAt(),
        result.updatedAt()
    );
  }
}
