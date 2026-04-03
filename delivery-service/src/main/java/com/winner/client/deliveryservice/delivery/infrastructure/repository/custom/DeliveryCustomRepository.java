package com.winner.client.deliveryservice.delivery.infrastructure.repository.custom;

import com.winner.client.deliveryservice.delivery.application.dto.query.SearchDeliveryQuery;
import com.winner.client.deliveryservice.delivery.domain.entity.Delivery;
import org.springframework.data.domain.Page;

public interface DeliveryCustomRepository {
  Page<Delivery> getAllDeliveries(SearchDeliveryQuery query);
}
