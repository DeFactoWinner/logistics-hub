package com.winner.client.companyservice.company.presentation.dto;

import com.winner.client.companyservice.company.application.dto.CompanyServiceDto;
import com.winner.client.companyservice.company.entity.vo.Type;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class CompanyRequestDto {

  @Builder
  @Getter
  public static class create{

    @NotBlank
    private String name;

    @NotNull(message = "주소는 필수입니다.")
    private String address;

    private String addressDetail;

    @NotNull(message = "허브ID는 필수입니다.")
    private UUID hubId;

    @NotNull(message = "생산업체/수령업체 타입을 선택해주세요.")
    private Type type;

    public CompanyServiceDto.create toServiceDto(){
      return CompanyServiceDto.create.builder()
          .name(this.name)
          .type(this.type)
          .hubId(this.hubId)
          .address(this.address)
          .addressDetail(this.addressDetail)
          .build();
    }

  }

}
