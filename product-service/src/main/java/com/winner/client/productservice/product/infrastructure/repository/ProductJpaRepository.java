package com.winner.client.productservice.product.infrastructure.repository;

import com.winner.client.productservice.product.domain.entity.Product;
import com.winner.client.productservice.product.domain.repository.ProductStockProjection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductJpaRepository extends JpaRepository<Product, UUID> {

  List<Product> findAllByDeletedAtIsNull();

  Optional<Product> findByIdAndDeletedAtIsNull(UUID productId);

  @Query(value = """
    SELECT new com.winner.client.productservice.product.domain.repository.ProductStockProjection(p, s)
    FROM Product p
    LEFT JOIN Stock s ON s.productId.productId = p.id
    WHERE p.deletedAt IS NULL
    """,
      countQuery = "SELECT count(p) FROM Product p WHERE p.deletedAt IS NULL")
  Page<ProductStockProjection> findAllWithStock(Pageable pageable);
}
