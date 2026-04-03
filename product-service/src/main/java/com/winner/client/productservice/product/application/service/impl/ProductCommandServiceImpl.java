package com.winner.client.productservice.product.application.service.impl;

import com.winner.client.productservice.product.application.service.ProductCommandService;
import com.winner.client.productservice.product.application.service.dto.command.CreateProductCommand;
import com.winner.client.productservice.product.application.service.dto.result.ProductResult;
import com.winner.client.productservice.product.domain.entity.Product;
import com.winner.client.productservice.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ProductCommandServiceImpl implements ProductCommandService {

  private final ProductRepository productRepository;

  @Override
  public ProductResult createProduct(CreateProductCommand command) {

    Product product = Product.create(command.productName(),
        command.companyId(),
        command.hubId(),
        command.description());

    productRepository.save(product);
    return ProductResult.from(product);
  }
}
