package com.winner.client.productservice.product.presentation;

import com.winner.client.global.response.ApiResponse;
import com.winner.client.global.response.CommonSuccessCode;
import com.winner.client.productservice.product.application.service.ProductCommandService;
import com.winner.client.productservice.product.application.service.dto.command.CreateProductCommand;
import com.winner.client.productservice.product.application.service.dto.result.ProductResult;
import com.winner.client.productservice.product.presentation.dto.reqeust.CreateProductRequest;
import com.winner.client.productservice.product.presentation.dto.response.CreateProductResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

  private final ProductCommandService productCommandService;

  @PostMapping
  public ResponseEntity<ApiResponse<CreateProductResponse>> createProduct(
      @Valid @RequestBody CreateProductRequest request
  ) {
    ProductResult result = productCommandService.createProduct(CreateProductCommand.from(request));

    return ResponseEntity.ok(ApiResponse.success(CommonSuccessCode.OK,CreateProductResponse.from(result)));
  }
}
