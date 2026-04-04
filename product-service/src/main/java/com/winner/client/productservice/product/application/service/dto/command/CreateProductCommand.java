package com.winner.client.productservice.product.application.service.dto.command;

import com.winner.client.global.exception.BusinessException;
import com.winner.client.productservice.common.exception.ProductErrorCode;
import com.winner.client.productservice.product.domain.vo.CompanyId;
import com.winner.client.productservice.product.domain.vo.HubId;
import com.winner.client.productservice.product.presentation.dto.reqeust.CreateProductRequest;

public record CreateProductCommand(
    String productName,
    CompanyId companyId,
    String description,
    HubId hubId
) {

  public static CreateProductCommand from(CreateProductRequest request) {

    if(request.hubId().equals(request.companyId())){
      throw new BusinessException(ProductErrorCode.IDENTICAL_IDS_NOT_ALLOWED);
    }

    return new CreateProductCommand(
        request.name(),
        CompanyId.of(request.companyId()),
        request.description(),
        HubId.of(request.hubId())
    );
  }
}
