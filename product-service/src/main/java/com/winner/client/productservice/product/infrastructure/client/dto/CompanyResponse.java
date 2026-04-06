package com.winner.client.productservice.product.infrastructure.client.dto;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CompanyResponse{

  private UUID companyId;
  private UUID hubId;
}




