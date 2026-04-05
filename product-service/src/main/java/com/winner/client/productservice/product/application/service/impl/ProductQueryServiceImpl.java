package com.winner.client.productservice.product.application.service.impl;

import com.winner.client.global.exception.BusinessException;
import com.winner.client.global.pagination.CommonPageRequest;
import com.winner.client.productservice.common.exception.ProductErrorCode;
import com.winner.client.productservice.product.application.service.ProductQueryService;
import com.winner.client.productservice.product.application.service.dto.query.FindProductDetailQuery;
import com.winner.client.productservice.product.application.service.dto.result.ProductDetailResult;
import com.winner.client.productservice.product.application.service.dto.result.ProductResult;
import com.winner.client.productservice.product.domain.entity.Product;
import com.winner.client.productservice.product.domain.repository.ProductRepository;
import com.winner.client.productservice.product.domain.repository.ProductStockProjection;
import com.winner.client.productservice.stock.domain.entity.Stock;
import com.winner.client.productservice.stock.domain.repository.StockRepository;
import com.winner.client.productservice.stock.domain.vo.ProductId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductQueryServiceImpl implements ProductQueryService {

  private final ProductRepository productRepository;
  private final StockRepository stockRepository;

  @Override
  public ProductResult getProduct(FindProductDetailQuery query) {
    Product product = productRepository.findByIdAndDeletedAtIsNull(query.productId())
        .orElseThrow(() -> new BusinessException(ProductErrorCode.PRODUCT_NOT_FOUND));

    return ProductResult.from(product);
  }

  @Override
  public ProductDetailResult getProductDetail(FindProductDetailQuery query) {
    Product product = productRepository.findByIdAndDeletedAtIsNull(query.productId())
        .orElseThrow(() -> new BusinessException((ProductErrorCode.PRODUCT_NOT_FOUND)));

    Stock stock = stockRepository.findByProductIdAndDeletedAtIsNull(ProductId.of(product.getId()))
        .orElseThrow(() -> new BusinessException(ProductErrorCode.STOCK_NOT_FOUND));

    return ProductDetailResult.of(product,stock);
  }

  @Override
  public Page<ProductDetailResult> getProductsDetailList(CommonPageRequest pageable) {

    Page<ProductStockProjection> result = productRepository.findAllWithStock(pageable);

    return result.map(projection -> {
      Product p = projection.getProduct();
      Stock s = projection.getStock();

      return ProductDetailResult.of(p, s);
    });
  }


}
