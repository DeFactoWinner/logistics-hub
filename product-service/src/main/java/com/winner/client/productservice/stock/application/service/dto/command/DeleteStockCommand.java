package com.winner.client.productservice.stock.application.service.dto.command;

import com.winner.client.productservice.stock.domain.vo.ProductId;

public record DeleteStockCommand(
    ProductId productId
) {

}
