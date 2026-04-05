package com.winner.client.productservice.product.application.event;

import com.winner.client.productservice.product.application.service.ProductCommandService;
import com.winner.client.productservice.product.domain.vo.StatusEnum;
import com.winner.client.productservice.stock.domain.event.StockUpdateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductEventListener {

  private final ProductCommandService productCommandService;

  @EventListener
  public void handle(StockUpdateEvent event){
    log.info(event.toString());

    StatusEnum statusEnum = event.isAvailable() ? StatusEnum.ON_SALE : StatusEnum.SOLD_OUT;

    productCommandService.updateProductStatus(event.productID(),statusEnum);
  }
}
