package com.winner.client.deliveryservice.delivery.application.dto.query;

import com.winner.client.deliveryservice.delivery.domain.enums.DeliveryStatus;
import com.winner.client.global.pagination.CommonPageRequest;
import com.winner.client.global.pagination.PageSortType;
import java.util.UUID;

public record SearchDeliveryQuery(
    UUID userId,
    String userRole,
    UUID referenceId,
    String keyword,
    DeliveryStatus status,
    PageSortType sortType,
    int page,
    int size
) {
  public static SearchDeliveryQuery of(
      UUID userId, String userRole, UUID referenceId, String keyword,
      String deliveryStatus, CommonPageRequest pageRequest) {
    return new SearchDeliveryQuery(
        userId,
        userRole,
        referenceId,
        keyword,
        DeliveryStatus.from(deliveryStatus),
        PageSortType.valueOf(pageRequest.getSort()),
        pageRequest.getPage(),
        pageRequest.getSize()
    );
  }
}
