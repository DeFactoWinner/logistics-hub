package com.winner.client.companyservice.company.presentation.dto;

import com.winner.client.companyservice.company.entity.dto.CompanyDto;
import com.winner.client.companyservice.company.entity.vo.Type;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CompanyResponseDto {

  private String name;
  private String address;
  private Type type;
  private UUID hubId;

  public static CompanyResponseDto from(CompanyDto.create dto){
    return CompanyResponseDto.builder()
        .name(dto.getName())
        .address(dto.getAddress())
        .type(dto.getType())
        .hubId(dto.getHubId())
        .build();
  }
}
