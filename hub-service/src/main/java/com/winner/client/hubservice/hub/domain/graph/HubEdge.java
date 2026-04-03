package com.winner.client.hubservice.hub.domain.graph;

import java.util.UUID;

public class HubEdge {

    private final UUID to;
    private final double distance;

    public HubEdge(UUID to, double distance) {
        this.to = to;
        this.distance = distance;
    }

    public UUID getTo() {
        return to;
    }

    public double getDistance() {
        return distance;
    }
}
