package com.winner.client.productservice.stock.domain.vo;

import com.winner.client.global.exception.BusinessException;
import com.winner.client.productservice.common.exception.StockErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductId {

  @Column(name = "product_id", nullable = false)
  private UUID productId;

  public ProductId(UUID productId) {
    validate(productId);
    this.productId = productId;
  }

  private void validate(UUID productId) {
    if (productId == null) {
      throw new BusinessException(StockErrorCode.PRODUCT_ID_REQUIRED);
    }
  }
  public static ProductId of(UUID productId){
    return new ProductId(productId);
  }
}