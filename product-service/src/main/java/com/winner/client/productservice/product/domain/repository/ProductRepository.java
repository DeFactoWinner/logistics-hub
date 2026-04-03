package com.winner.client.productservice.product.domain.repository;

import com.winner.client.productservice.product.domain.entity.Product;

public interface ProductRepository {

  Product save(Product product);
}
