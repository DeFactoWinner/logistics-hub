package com.winner.client.productservice.product.application.service.impl;

import com.winner.client.productservice.product.application.service.ProductCommandService;
import com.winner.client.productservice.product.application.service.dto.command.CreateProductCommand;
import com.winner.client.productservice.product.application.service.dto.result.ProductResult;
import com.winner.client.productservice.product.domain.entity.Product;
import com.winner.client.productservice.product.domain.event.ProductCreateEvent;
import com.winner.client.productservice.product.domain.repository.ProductRepository;
import com.winner.client.productservice.stock.domain.vo.ProductId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ProductCommandServiceImpl implements ProductCommandService {

  private final ProductRepository productRepository;
  private final ApplicationEventPublisher eventPublisher;

  @Override
  public ProductResult createProduct(CreateProductCommand command) {

    Product product = Product.create(command.productName(),
        command.companyId(),
        command.hubId(),
        command.description());

    productRepository.save(product);

    eventPublisher.publishEvent(new ProductCreateEvent(new ProductId(product.getId())));

    return ProductResult.from(product);
  }
}
