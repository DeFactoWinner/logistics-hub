package com.winner.client.deliveryservice.delivery.domain.enums;

public enum DeliveryStatus {
  CREATED,
  HUB_WAITING,
  HUB_MOVING,
  DESTINATION_ARRIVED,
  FOR_VENDOR_MOVING,
  COMPLETED,
  CANCELLED,
  CANCEL_REQUESTED
}
