package com.winner.client.productservice.product.presentation.dto.reqeust;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CreateProductRequest (
    @NotNull(message = "이름은 필수입니다.")
    String name,

    String description,

    @NotNull(message = "회사ID는 필수입니다.")
    UUID companyId,

    @NotNull(message = "허브ID는 필수입니다.")
    UUID hubId
){
}
