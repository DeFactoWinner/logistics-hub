package com.winner.client.deliveryservice.delivery.domain.enums;

import java.util.Set;

public enum DeliveryStatus {

  CREATED {
    public Set<DeliveryStatus> nextStates() {
      return Set.of(HUB_WAITING, CANCELLED);
    }
  },
  HUB_WAITING {
    public Set<DeliveryStatus> nextStates() {
      return Set.of(HUB_MOVING, CANCELLED);
    }
  },
  HUB_MOVING {
    public Set<DeliveryStatus> nextStates() {
      return Set.of(DESTINATION_ARRIVED);
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
}
