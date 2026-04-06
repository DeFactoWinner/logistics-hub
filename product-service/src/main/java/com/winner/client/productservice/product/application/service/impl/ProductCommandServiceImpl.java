package com.winner.client.productservice.product.application.service.impl;

import com.winner.client.global.exception.BusinessException;
import com.winner.client.global.security.CustomUserPrincipal;
import com.winner.client.productservice.common.exception.ProductErrorCode;
import com.winner.client.productservice.product.application.service.ProductCommandService;
import com.winner.client.productservice.product.application.service.dto.command.CreateProductCommand;
import com.winner.client.productservice.product.application.service.dto.command.UpdateProductCommand;
import com.winner.client.productservice.product.application.service.dto.result.ProductResult;
import com.winner.client.productservice.product.application.service.port.CompanyPort;
import com.winner.client.productservice.product.application.service.validate.ProductValidate;
import com.winner.client.productservice.product.domain.entity.Product;
import com.winner.client.productservice.product.domain.event.ProductCreateEvent;
import com.winner.client.productservice.product.domain.event.ProductDeleteEvent;
import com.winner.client.productservice.product.domain.repository.ProductRepository;
import com.winner.client.productservice.product.domain.vo.CompanyId;
import com.winner.client.productservice.product.domain.vo.HubId;
import com.winner.client.productservice.product.domain.vo.StatusEnum;
import com.winner.client.productservice.product.infrastructure.repository.client.dto.CompanyResponse;
import com.winner.client.productservice.stock.domain.vo.ProductId;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ProductCommandServiceImpl implements ProductCommandService {

  private final ProductRepository productRepository;
  private final ApplicationEventPublisher eventPublisher;
  private final CompanyPort companyPort;
  private final ProductValidate productValidate;

  @Override
  public ProductResult createProduct(CreateProductCommand command,
      CustomUserPrincipal userPrincipal) {

    CompanyResponse response =
        companyPort.getCompany(command.companyId().getCompanyId());

    UUID hubId = response.hubId();
    HubId hub = HubId.of(hubId);

    if(!hub.getHubId().equals(command.hubId().getHubId())){
      throw new BusinessException(ProductErrorCode.HUB_NOT_FOUND);
    }

    UUID companyId = response.companyId();
    CompanyId company = CompanyId.of(companyId);

    productValidate.upsertProduct(response,userPrincipal);

    Product product = Product.create(command.productName(),
        company,
        hub,
        command.description());

    productRepository.save(product);

    eventPublisher.publishEvent(new ProductCreateEvent(new ProductId(product.getId())));

    return ProductResult.from(product);
  }

  @Override
  public ProductResult updateProduct(UpdateProductCommand command,
      CustomUserPrincipal userPrincipal) {

    Product product = productRepository.findByIdAndDeletedAtIsNull(command.productId())
        .orElseThrow(() -> new BusinessException(ProductErrorCode.PRODUCT_NOT_FOUND));

    try {
      companyPort.getCompany(product.getCompanyId().getCompanyId());
    } catch (Exception e) {
      log.error("여기서 죽었네! 범인은: {}", e.getMessage());
    }

    product.updateInfo(
        command.name(),
        command.description()
    );

    return ProductResult.from(product);
  }

  @Override
  public void deleteProduct(UUID productId, CustomUserPrincipal userPrincipal) {
    Product product = productRepository.findByIdAndDeletedAtIsNull(productId)
        .orElseThrow(() -> new BusinessException(ProductErrorCode.PRODUCT_NOT_FOUND));

    CompanyResponse response =
        companyPort.getCompany(product.getCompanyId().getCompanyId());

    productValidate.deleteProduct(response,userPrincipal);

    product.softDelete(product.getId());

    eventPublisher.publishEvent(new ProductDeleteEvent(new ProductId(product.getId())));
  }

  @Override
  public void updateProductStatus(ProductId productId, StatusEnum status) {
    Product product = productRepository.findByIdAndDeletedAtIsNull(productId.getProductId())
        .orElseThrow(() -> new BusinessException(ProductErrorCode.PRODUCT_NOT_FOUND));

    product.updateStatus(status);
  }
}
