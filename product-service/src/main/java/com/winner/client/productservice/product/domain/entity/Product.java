package com.winner.client.productservice.product.domain.entity;

import com.winner.client.global.entity.BaseAuditEntity;
import com.winner.client.global.exception.BusinessException;
import com.winner.client.productservice.common.exception.ProductErrorCode;
import com.winner.client.productservice.product.domain.vo.CompanyId;
import com.winner.client.productservice.product.domain.vo.HubId;
import com.winner.client.productservice.product.domain.vo.StatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Table(name = "p_products",
uniqueConstraints = {
    @UniqueConstraint(name = "uk_product_name_company_id", columnNames = {"name","company_id"})
})
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseAuditEntity {

  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue
  @UuidGenerator
  private UUID id;

  @Column(name = "name", nullable = false)
  private String name;

  @Embedded
  private CompanyId companyId;

  @Embedded
  private HubId hubId;

  @Column(name = "description")
  private String description;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private StatusEnum statusEnum;

  private Product(String name, CompanyId companyId, HubId hubId, String description, StatusEnum statusEnum) {
    this.name = name;
    this.companyId = companyId;
    this.hubId = hubId;
    this.description = description;
    this.statusEnum = statusEnum;
  }

  public static Product create(String name, CompanyId companyId, HubId hubId, String description) {

    if(name == null || name.isBlank()){
      throw new BusinessException(ProductErrorCode.COMPANY_ID_REQUIRED);
    }

    return new Product(name,companyId,hubId,description,StatusEnum.SOLD_OUT);
  }

  public void updateInfo(String name, String description) {

    if(name != null) this.name = name;
    if(description != null) this.description = description;
  }

  public void updateStatus(StatusEnum statusEnum) {
    this.statusEnum = statusEnum;
  }
}
