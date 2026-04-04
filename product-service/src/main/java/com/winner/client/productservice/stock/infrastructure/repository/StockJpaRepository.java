package com.winner.client.productservice.stock.infrastructure.repository;

import com.winner.client.productservice.stock.domain.entity.Stock;
import com.winner.client.productservice.stock.domain.vo.ProductId;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockJpaRepository extends JpaRepository<Stock, UUID> {

  Stock findByProductIdAndDeletedAtIsNull(ProductId productId);

}
