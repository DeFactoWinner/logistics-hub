package com.winner.client.deliveryservice.delivery.presentation.dto.response;

import com.winner.client.deliveryservice.delivery.application.dto.result.FindDeliveryRouteResult;
import java.util.List;

public record ListDeliveryRouteResponse(
    List<GetDeliveryRouteResponse> routes, int routesCount){
  public static ListDeliveryRouteResponse from(List<FindDeliveryRouteResult> results) {
    List<GetDeliveryRouteResponse> responses = results.stream()
        .map(GetDeliveryRouteResponse::from)
        .toList();
    return new ListDeliveryRouteResponse(responses, responses.size());
  }
}
