package com.winner.client.companyservice.company.entity.dto;

import com.winner.client.companyservice.company.entity.vo.Type;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class CompanyDto {

  @Getter
  @Builder
  public static class create{
    private String name;
    private String address;
    private Type type;
    private UUID hubId;
  }

}
