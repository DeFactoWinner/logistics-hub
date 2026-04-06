package com.winner.client.productservice.product.infrastructure.repository.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CompanyResponse(
    @JsonProperty("companyId") UUID companyId,
    @JsonProperty("hubId") UUID hubId
) {

}
