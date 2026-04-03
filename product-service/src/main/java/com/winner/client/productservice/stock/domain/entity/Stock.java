package com.winner.client.productservice.stock.domain.entity;

import com.winner.client.global.entity.BaseAuditEntity;
import com.winner.client.productservice.stock.domain.vo.ProductId;
import com.winner.client.productservice.stock.domain.vo.Quantity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Getter
@NoArgsConstructor( access = AccessLevel.PROTECTED)
@Entity
@Table(name = "p_stocks")
public class Stock extends BaseAuditEntity {

  @Id
  @UuidGenerator
  @Column(name = "id", nullable = false)
  private UUID id;

  @Embedded
  private ProductId productId;

  @Embedded
  private Quantity quantity;

  public Stock(ProductId productId, Quantity quantity) {
    this.productId = productId;
    this.quantity = quantity;
  }

  public static Stock create(ProductId productId) {
    return new Stock(productId, new Quantity(0));
  }

  public void increase(int amount) {
    quantity = quantity.add(amount);
  }

  public void decrease( int amount) {
    quantity = quantity.reduce(amount);
  }
}
