package com.winner.orderservice.order.domain.enums;

import java.util.Set;

public enum OrderStatus {

    PENDING {
        @Override
        public Set<OrderStatus> nextStates() {
            return Set.of(CONFIRMED, CANCELLED);
        }
        @Override
        public boolean isDeletable() { return true; }
    },
    CONFIRMED {
        @Override
        public Set<OrderStatus> nextStates() {
            return Set.of(SHIPPING, CANCELLED);
        }
        @Override
        public boolean isDeletable() { return true; }
    },
    SHIPPING {
        @Override
        public Set<OrderStatus> nextStates() {
            return Set.of(DELIVERED);
        }
        @Override
        public boolean isDeletable() { return false; }
    },
    DELIVERED {
        @Override
        public Set<OrderStatus> nextStates() {
            return Set.of();
        }
        @Override
        public boolean isDeletable() { return false; }
    },
    CANCELLED {
        @Override
        public Set<OrderStatus> nextStates() {
            return Set.of();
        }
        @Override
        public boolean isDeletable() { return false; }
    };

    public abstract Set<OrderStatus> nextStates();
    public abstract boolean isDeletable();

    public boolean canTransitionTo(OrderStatus next) {
        return nextStates().contains(next);
    }

    public boolean isEditable() {
        return this == PENDING || this == CONFIRMED;
    }

    public boolean isCancellable() {
        return canTransitionTo(CANCELLED);
    }
}