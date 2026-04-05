package com.winner.client.productservice.product.infrastructure.repository;

import com.winner.client.productservice.product.domain.entity.Product;
import com.winner.client.productservice.product.domain.repository.ProductRepository;
import com.winner.client.productservice.product.domain.repository.ProductStockProjection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ProductRepositoryImpl implements ProductRepository {

  private final ProductJpaRepository productJpaRepository;

  @Override
  public Product save(Product product) {
    return productJpaRepository.save(product);
  }

  @Override
  public Optional<Product> findByIdAndDeletedAtIsNull(UUID productId) {
    return productJpaRepository.findByIdAndDeletedAtIsNull(productId);
  }

  @Override
  public List<Product> findAllByDeletedAtIsNull() {
    return productJpaRepository.findAllByDeletedAtIsNull();
  }

  @Override
  public Page<ProductStockProjection> findAllWithStock(Pageable pageable) {
    return productJpaRepository.findAllWithStock(pageable);
  }


}
