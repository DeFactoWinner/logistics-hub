package com.winner.client.deliveryservice.delivery.domain.enums;

import java.util.Set;

public enum DeliveryRouteStatus {

  WAITING {
    @Override
    public Set<DeliveryRouteStatus> nextStates() {
      return Set.of(IN_PROGRESS, CANCELLED);
    }
  },

  IN_PROGRESS {
    @Override
    public Set<DeliveryRouteStatus> nextStates() {
      return Set.of(COMPLETED);
    }
  },

  COMPLETED {
    @Override
    public Set<DeliveryRouteStatus> nextStates() {
      return Set.of();
    }
  },

  CANCELLED {
    @Override
    public Set<DeliveryRouteStatus> nextStates() {
      return Set.of();
    }
  };

  public abstract Set<DeliveryRouteStatus> nextStates();

  public boolean canTransitionTo(DeliveryRouteStatus next) {
    return next != null && nextStates().contains(next);
  }
}