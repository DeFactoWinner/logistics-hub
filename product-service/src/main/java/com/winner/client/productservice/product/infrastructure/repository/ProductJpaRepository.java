package com.winner.client.productservice.product.infrastructure.repository;

import com.winner.client.productservice.product.domain.entity.Product;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<Product, UUID> {

}
