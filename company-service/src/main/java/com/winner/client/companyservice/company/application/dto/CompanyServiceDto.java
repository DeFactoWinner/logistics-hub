package com.winner.client.companyservice.company.application.dto;

import com.winner.client.companyservice.company.entity.vo.Type;
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
}
