package com.winner.client.productservice.stock.infrastructure.repository;

import com.winner.client.productservice.stock.domain.entity.Stock;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockJpaRepository extends JpaRepository<Stock, UUID> {

}
