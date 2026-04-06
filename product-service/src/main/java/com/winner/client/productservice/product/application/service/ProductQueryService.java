package com.winner.client.productservice.product.application.service;

import com.winner.client.global.pagination.CommonPageRequest;
import com.winner.client.productservice.product.application.service.dto.query.FindProductDetailQuery;
import com.winner.client.productservice.product.application.service.dto.result.ProductDetailResult;
import com.winner.client.productservice.product.application.service.dto.result.ProductResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface ProductQueryService {

  ProductResult getProduct(FindProductDetailQuery query);

  Page<ProductDetailResult> getProductsDetailList(Pageable pageable);

  ProductDetailResult getProductDetail(FindProductDetailQuery query);

}
