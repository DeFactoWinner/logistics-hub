package com.winner.client.productservice.product.application.service;

import com.winner.client.productservice.product.application.service.dto.command.CreateProductCommand;
import com.winner.client.productservice.product.application.service.dto.command.UpdateProductCommand;
import com.winner.client.productservice.product.application.service.dto.result.ProductResult;
import com.winner.client.productservice.product.domain.vo.StatusEnum;
import com.winner.client.productservice.stock.domain.vo.ProductId;
import java.util.UUID;

public interface ProductCommandService {

  ProductResult createProduct(CreateProductCommand command);

  ProductResult updateProduct(UpdateProductCommand command);

  void deleteProduct(UUID productId);

  void updateProductStatus(ProductId productId, StatusEnum status);
}
