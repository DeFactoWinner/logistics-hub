package com.winner.client.companyservice.company.entity.entity;

import com.winner.client.companyservice.company.entity.vo.CompanyAddress;
import com.winner.client.companyservice.company.entity.vo.CompanyLocation;
import com.winner.client.companyservice.company.entity.vo.HubId;
import com.winner.client.companyservice.company.entity.vo.Type;
import com.winner.client.global.entity.BaseAuditEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Getter
@Builder
@Table(name = "p_company", schema = "company_db")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Company extends BaseAuditEntity {

  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue
  @UuidGenerator
  private UUID id;

  @Column(name = "name", nullable = false)
  private String companyName;

  @Column(name = "type", nullable = false)
  private Type type;

  @Embedded
  private HubId hubId;

  @Embedded
  private CompanyAddress address;

  @Embedded
  private CompanyLocation location;

  public static Company create(String companyName, Type type, HubId hubId,
      CompanyLocation location, CompanyAddress address){

    return Company.builder()
        .companyName(companyName)
        .type(type)
        .hubId(hubId)
        .location(location)
        .address(address)
        .build();
  }




}
