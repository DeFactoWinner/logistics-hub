package com.winner.client.productservice.product.presentation.dto.response;

import com.winner.client.productservice.product.application.service.dto.result.ProductDetailResult;
import org.springframework.data.domain.Page;

public record ListProductResponse(
    Page<ProductDetailResult> list,
    Long totalCount
) {

  public static ListProductResponse from(Page<ProductDetailResult> list) {
    return new ListProductResponse(list, list.getTotalElements());
  }
}
