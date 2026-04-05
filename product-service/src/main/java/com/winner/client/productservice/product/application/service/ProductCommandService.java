package com.winner.client.productservice.product.application.service;

import com.winner.client.productservice.product.application.service.dto.command.CreateProductCommand;
import com.winner.client.productservice.product.application.service.dto.result.ProductResult;

public interface ProductCommandService {

  ProductResult createProduct(CreateProductCommand command);

}
