package com.winner.client.productservice.product.domain.repository;

import com.winner.client.productservice.product.domain.entity.Product;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepository {

  Product save(Product product);

  Product findByIdAndDeletedAtIsNull(UUID productId);

  List<Product> findAllByDeletedAtIsNull();

  Page<Object[]> findAllWithStock(Pageable pageable);
}
