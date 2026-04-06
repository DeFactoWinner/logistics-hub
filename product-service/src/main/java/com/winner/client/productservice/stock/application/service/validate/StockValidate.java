package com.winner.client.productservice.stock.application.service.validate;

import com.winner.client.global.exception.BusinessException;
import com.winner.client.global.exception.CommonErrorCode;
import com.winner.client.global.security.CustomUserPrincipal;
import com.winner.client.productservice.common.exception.ProductErrorCode;
import com.winner.client.productservice.product.domain.entity.Product;
import com.winner.client.productservice.product.domain.repository.ProductRepository;
import com.winner.client.productservice.stock.domain.entity.Stock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class StockValidate {

  private final ProductRepository productRepository;

  public void updateStock(Stock stock,
      CustomUserPrincipal userPrincipal) {

    Product product = productRepository.findByIdAndDeletedAtIsNull(stock.getProductId().getProductId())
        .orElseThrow(() -> new BusinessException(ProductErrorCode.COMPANY_NOT_FOUND));

    log.info("userRole : {}",userPrincipal.role());
    log.info("product.getHubId().getHubId() : {}",product.getHubId().getHubId());

    if("MASTER".equals(userPrincipal.role())) return;

    if("HUB_MANAGER".equals(userPrincipal.role())){
      if(userPrincipal.referenceId().equals(product.getHubId().getHubId())) return;
    }

    if("COMPANY_MANAGER".equals(userPrincipal.role())){
      if(userPrincipal.referenceId().equals(product.getCompanyId().getCompanyId())) return;
    }

    throw new BusinessException(CommonErrorCode.FORBIDDEN);
  }
}
