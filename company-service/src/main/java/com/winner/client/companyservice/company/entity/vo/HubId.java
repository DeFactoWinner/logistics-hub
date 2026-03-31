package com.winner.client.companyservice.company.entity.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class HubId {

  @Column(name = "hub_id", nullable = false)
  private UUID hubId;

  public static HubId of(UUID hubId){
    if(hubId == null){
      throw new IllegalArgumentException("ID는 필수 값입니다.");}
    return new HubId(hubId);
  }

}
