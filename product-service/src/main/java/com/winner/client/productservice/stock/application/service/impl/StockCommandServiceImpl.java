package com.winner.client.productservice.stock.application.service.impl;

import com.winner.client.global.exception.BusinessException;
import com.winner.client.productservice.common.exception.StockErrorCode;
import com.winner.client.productservice.stock.application.service.StockCommandService;
import com.winner.client.productservice.stock.application.dto.command.CreateStockCommand;
import com.winner.client.productservice.stock.application.dto.command.DeleteStockCommand;
import com.winner.client.productservice.stock.application.dto.command.UpdateStockCommand;
import com.winner.client.productservice.stock.application.dto.result.StockResult;
import com.winner.client.productservice.stock.domain.entity.Stock;
import com.winner.client.productservice.stock.domain.event.StockUpdateEvent;
import com.winner.client.productservice.stock.domain.repository.StockRepository;
import com.winner.client.productservice.stock.domain.vo.ProductId;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class StockCommandServiceImpl implements StockCommandService {

  private final StockRepository stockRepository;
  private final ApplicationEventPublisher eventPublisher;

  @Override
  public void createStock(CreateStockCommand command) {

    Stock stock = Stock.create(command.productId());
    stockRepository.save(stock);
  }

  @Override
  public void deleteStock(DeleteStockCommand command) {
    Stock stock = stockRepository.findByProductIdAndDeletedAtIsNull(command.productId())
        .orElseThrow(() -> new BusinessException(StockErrorCode.STOCK_NOT_FOUND));

    stock.softDelete(stock.getId());
  }

  @Override
  public StockResult updateStock(UpdateStockCommand command) {

    Stock stock = stockRepository.findByProductIdAndDeletedAtIsNull(command.productId())
        .orElseThrow(() -> new BusinessException(StockErrorCode.STOCK_NOT_FOUND));

    boolean statusChanged = stock.updateQuantityAndCheckStatus(command.amount());

    if(statusChanged){
      eventPublisher.publishEvent(
          new StockUpdateEvent(ProductId.of(stock.getProductId().getProductId()),stock.isAvailable()));
    }

    return StockResult.from(stock);
  }
}
