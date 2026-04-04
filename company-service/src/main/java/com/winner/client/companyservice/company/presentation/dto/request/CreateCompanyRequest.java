package com.winner.client.companyservice.company.presentation.dto.request;

import com.winner.client.companyservice.company.domain.vo.Type;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CreateCompanyRequest(

    @NotBlank
    String name,

    @NotNull(message = "주소는 필수입니다.")
    String address,

    String addressDetail,

    @NotNull(message = "허브ID는 필수입니다.")
    UUID hubId,

    @NotNull(message = "생산업체/수령업체 타입을 선택해주세요.")
    Type type
) {

}
