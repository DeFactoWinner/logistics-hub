package com.winner.client.productservice.stock.application.service.event;

import com.winner.client.productservice.product.domain.event.ProductCreateEvent;
import com.winner.client.productservice.product.domain.event.ProductDeleteEvent;
import com.winner.client.productservice.stock.application.service.StockCommandService;
import com.winner.client.productservice.stock.application.dto.command.CreateStockCommand;
import com.winner.client.productservice.stock.application.dto.command.DeleteStockCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class StockEventListener {

  private final StockCommandService stockCommandService;

  @EventListener
  public void handle(ProductCreateEvent event) {
    log.info(event.toString());
    stockCommandService.createStock(new CreateStockCommand(event.productId()));
  }

  @EventListener
  public void handle(ProductDeleteEvent event) {
    log.info(event.toString());
    stockCommandService.deleteStock(new DeleteStockCommand(event.productId()));
  }
}
