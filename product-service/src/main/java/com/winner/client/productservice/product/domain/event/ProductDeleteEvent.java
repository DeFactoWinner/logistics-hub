package com.winner.client.productservice.product.domain.event;

import com.winner.client.productservice.stock.domain.vo.ProductId;

public record ProductDeleteEvent(
    ProductId productId
) {

}
