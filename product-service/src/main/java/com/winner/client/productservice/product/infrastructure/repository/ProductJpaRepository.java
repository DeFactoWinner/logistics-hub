package com.winner.client.productservice.product.infrastructure.repository;

import com.winner.client.productservice.product.domain.entity.Product;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductJpaRepository extends JpaRepository<Product, UUID> {

  List<Product> findAllByDeletedAtIsNull();

  Product findByIdAndDeletedAtIsNull(UUID productId);

  @Query("""
        SELECT p, s FROM Product p
        LEFT JOIN Stock s ON s.productId = p.id
        WHERE p.deletedAt IS NULL
        """)
  Page<Object[]> findAllWithStock(Pageable pageable);
}
