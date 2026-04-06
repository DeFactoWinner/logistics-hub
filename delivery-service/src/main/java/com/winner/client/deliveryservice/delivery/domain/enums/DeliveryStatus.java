package com.winner.client.deliveryservice.delivery.domain.enums;

import java.util.Set;

public enum DeliveryStatus {

  PENDING {
    public Set<DeliveryStatus> nextStates() {
      return Set.of(CANCELLED, HUB_MOVING);
    }
  },
  HUB_MOVING {
    public Set<DeliveryStatus> nextStates() {
      return Set.of(DESTINATION_ARRIVED, FOR_VENDOR_MOVING);
    }
  },
  DESTINATION_ARRIVED {
    public Set<DeliveryStatus> nextStates() {
      return Set.of(FOR_VENDOR_MOVING);
    }
  },
  FOR_VENDOR_MOVING {
    public Set<DeliveryStatus> nextStates() {
      return Set.of(COMPLETED);
    }
  },
  COMPLETED {
    public Set<DeliveryStatus> nextStates() {
      return Set.of();
    }
  },
  CANCELLED {
    public Set<DeliveryStatus> nextStates() {
      return Set.of();
    }
  };

  public abstract Set<DeliveryStatus> nextStates();

  public boolean canTransitionTo(DeliveryStatus next) {
    return next != null && nextStates().contains(next);
  }

  public static DeliveryStatus from(String value) {
    if (value == null || value.isBlank()) return null;
    try {
      return DeliveryStatus.valueOf(value.toUpperCase());
    } catch (IllegalArgumentException e) {
      return null;
    }
  }
}
