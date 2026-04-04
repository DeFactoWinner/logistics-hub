package com.winner.client.productservice.product.presentation;

import com.winner.client.global.response.ApiResponse;
import com.winner.client.global.response.CommonSuccessCode;
import com.winner.client.productservice.product.application.service.ProductCommandService;
import com.winner.client.productservice.product.application.service.ProductQueryService;
import com.winner.client.productservice.product.application.service.dto.command.CreateProductCommand;
import com.winner.client.productservice.product.application.service.dto.query.FindProductDetailQuery;
import com.winner.client.productservice.product.application.service.dto.result.ProductDetailResult;
import com.winner.client.productservice.product.application.service.dto.result.ProductResult;
import com.winner.client.productservice.product.presentation.dto.reqeust.CreateProductRequest;
import com.winner.client.productservice.product.presentation.dto.response.CreateProductResponse;
import com.winner.client.productservice.product.presentation.dto.response.GetProductDetailResponse;
import com.winner.client.productservice.product.presentation.dto.response.ListProductResponse;
import com.winner.client.productservice.stock.application.service.StockQueryService;
import com.winner.client.productservice.stock.application.service.dto.result.StockResult;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

  private final ProductCommandService productCommandService;
  private final ProductQueryService productQueryService;
  private final StockQueryService stockQueryService;

  @PostMapping
  public ResponseEntity<ApiResponse<CreateProductResponse>> createProduct(
      @Valid @RequestBody CreateProductRequest request
  ) {
    ProductResult result = productCommandService.createProduct(CreateProductCommand.from(request));

    return ResponseEntity.ok(ApiResponse.success(CommonSuccessCode.OK,CreateProductResponse.from(result)));
  }

  @GetMapping("/{product_id}")
  public ResponseEntity<ApiResponse<GetProductDetailResponse>> getProduct(
      @PathVariable UUID product_id){

    ProductDetailResult result = productQueryService.getProductDetail(FindProductDetailQuery.from(product_id));

    return ResponseEntity.ok(ApiResponse.success(CommonSuccessCode.OK,
        GetProductDetailResponse.from(result)));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<ListProductResponse>> getProductList(Pageable Pageable){
    Page<ProductDetailResult> result = productQueryService.getProductsDetailList(Pageable);

    return ResponseEntity.ok(ApiResponse.success(CommonSuccessCode.OK,
        ListProductResponse.of(result,(long)result.getTotalElements())));
  }
}
