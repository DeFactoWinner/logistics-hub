package com.winner.client.companyservice.company.application.dto;

import com.winner.client.companyservice.company.domain.vo.Type;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

public class CompanyServiceDto {

  @Getter
  @Builder
  public static class create{
    private String name;
    private String address;
    private String addressDetail;
    private Type type;
    private UUID hubId;
  }

  @Getter
  @Builder
  public static class update{
    private String name;
    private String address;
    private String addressDetail;
    private UUID hubId;


  }
}
