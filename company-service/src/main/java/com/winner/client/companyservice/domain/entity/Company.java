package com.winner.client.companyservice.domain.entity;

import com.winner.client.companyservice.domain.vo.CompanyLocation;
import com.winner.client.companyservice.domain.vo.HubId;
import com.winner.client.companyservice.domain.vo.Type;
import com.winner.client.companyservice.infrastructure.service.GeocodingService;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Getter
@Builder
@Table(name = "p_company")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Company {

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
  private CompanyLocation location;

  public static Company create(UUID id, String companyName, Type type, HubId hubId,
      CompanyLocation location, GeocodingService geocodingService){

    CompanyLocation cl = CompanyLocation.of(location.getAddress(), geocodingService);

    return Company.builder()
        .id(id)
        .companyName(companyName)
        .type(type)
        .hubId(hubId)
        .location(cl)
        .build();
  }



}
