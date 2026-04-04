package com.winner.client.productservice.product.infrastructure.repository;

import com.querydsl.jpa.JPQLQueryFactory;
import com.winner.client.productservice.product.domain.entity.Product;
import com.winner.client.productservice.product.domain.repository.ProductRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ProductRepositoryImpl implements ProductRepository {

  private final ProductJpaRepository productJpaRepository;
  private final JPQLQueryFactory jPQLQueryFactory;

  @Override
  public Product save(Product product) {
    return productJpaRepository.save(product);
  }

  @Override
  public Product findByIdAndDeletedAtIsNull(UUID productId) {
    return productJpaRepository.findByIdAndDeletedAtIsNull(productId);
  }

  @Override
  public List<Product> findAllByDeletedAtIsNull() {
    return productJpaRepository.findAllByDeletedAtIsNull();
  }

  @Override
  public Page<Object[]> findAllWithStock(Pageable pageable) {
    return productJpaRepository.findAllWithStock(pageable);
  }


}
