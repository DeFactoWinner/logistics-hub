package com.winner.client.productservice.product.domain.entity;

import com.winner.client.global.entity.BaseAuditEntity;
import com.winner.client.productservice.product.domain.vo.CompanyId;
import com.winner.client.productservice.product.domain.vo.HubId;
import com.winner.client.productservice.product.domain.vo.StatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Table(name = "p_products")
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
  private HubId hub_id;

  @Column(name = "description")
  private String description;

  @Column(name = "status")
  private StatusEnum stausEnum;

  private Product(String name, CompanyId companyId, HubId hubId, String description, StatusEnum statusEnum) {
    this.name = name;
    this.companyId = companyId;
    this.hub_id = hubId;
    this.description = description;
    this.stausEnum = statusEnum;
  }

  public static Product create(String name, CompanyId companyId, HubId hubId, String description) {

    return new Product(name,companyId,hubId,description,StatusEnum.INACTIVE);
  }

  public void update(String name, String description, StatusEnum statusEnum) {

    if(name != null) this.name = name;
    if(description != null) this.description = description;
    if(statusEnum != null) this.stausEnum = statusEnum;
  }


}
