package com.winner.client.productservice.stock.application.dto.command;

import com.winner.client.productservice.stock.domain.vo.ProductId;

public record CreateStockCommand(
    ProductId productId
) {

}
