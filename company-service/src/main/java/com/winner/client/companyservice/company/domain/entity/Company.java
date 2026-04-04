package com.winner.client.companyservice.company.domain.entity;

import com.winner.client.global.exception.BusinessException;
import com.winner.client.companyservice.common.exception.CompanyErrorCode;
import com.winner.client.companyservice.company.domain.vo.CompanyAddress;
import com.winner.client.companyservice.company.domain.vo.CompanyLocation;
import com.winner.client.companyservice.company.domain.vo.HubId;
import com.winner.client.companyservice.company.domain.vo.Type;
import com.winner.client.global.entity.BaseAuditEntity;
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

@Entity
@Getter
@Table(
    name = "p_companies",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_company_name_address",
            columnNames = {"name", "address"}
        )
    }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Company extends BaseAuditEntity {

  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue
  @UuidGenerator
  private UUID id;

  @Column(name = "name", nullable = false)
  private String companyName;

  @Enumerated(EnumType.STRING)
  @Column(name = "type", nullable = false)
  private Type type;

  @Embedded
  private HubId hubId;

  @Embedded
  private CompanyAddress address;

  @Embedded
  private CompanyLocation location;

  public Company(String companyName, Type type, HubId hubId, CompanyLocation location, CompanyAddress address) {
    this.companyName = companyName;
    this.type = type;
    this.hubId = hubId;
    this.location = location;
    this.address = address;
  }


  public static Company create(String companyName, Type type, HubId hubId,
      CompanyLocation location, CompanyAddress address){

    if(companyName == null || companyName.isBlank()){
      throw new BusinessException(CompanyErrorCode.NAME_REQUIRED);
    }
    return new Company(companyName, type, hubId, location, address);
  }

  public void updateCompany(String companyName, HubId hubId, CompanyLocation location, CompanyAddress address){

      if(companyName != null) this.companyName = companyName;

      if(hubId != null) this.hubId = hubId;

      if(location != null) this.location = location;

      if(address != null) this.address = address;

  }


}
