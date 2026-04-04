package com.winner.client.productservice.product.application.service.impl;

import com.winner.client.productservice.product.application.service.ProductQueryService;
import com.winner.client.productservice.product.application.service.dto.query.FindProductDetailQuery;
import com.winner.client.productservice.product.application.service.dto.result.ProductDetailResult;
import com.winner.client.productservice.product.application.service.dto.result.ProductResult;
import com.winner.client.productservice.product.domain.entity.Product;
import com.winner.client.productservice.product.domain.repository.ProductRepository;
import com.winner.client.productservice.stock.domain.entity.Stock;
import com.winner.client.productservice.stock.domain.repository.StockRepository;
import com.winner.client.productservice.stock.domain.vo.ProductId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductQueryServiceImpl implements ProductQueryService {

  private final ProductRepository productRepository;
  private final StockRepository stockRepository;

  @Override
  public ProductResult getProduct(FindProductDetailQuery query) {
    Product product = productRepository.findByIdAndDeletedAtIsNull(query.productId());
    return ProductResult.from(product);
  }

  @Override
  public ProductDetailResult getProductDetail(FindProductDetailQuery query) {
    Product product = productRepository.findByIdAndDeletedAtIsNull(query.productId());
    Stock stock = stockRepository.findByProductIdAndDeletedAtIsNull(ProductId.of(product.getId()));
    return ProductDetailResult.of(product,stock);
  }

  @Override
  public Page<ProductDetailResult> getProductsDetailList(Pageable pageable) {

    return productRepository.findAllWithStock(pageable)
        .map(arr -> {
          Product p = (Product) arr[0];
          Stock s = (Stock) arr[1];

          return ProductDetailResult.of(p, s);
        });
  }


}
