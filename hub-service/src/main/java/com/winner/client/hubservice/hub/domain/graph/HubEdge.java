package com.winner.client.hubservice.hub.domain.graph;

import java.util.UUID;

public class HubEdge {

    private final UUID to;
    private final double distance;
    private final double time;

    public HubEdge(UUID to, double distance, double time) {
        this.to = to;
        this.distance = distance;
        this.time = time;
    }

    public UUID getTo() {
        return to;
    }

    public double getDistance() {
        return distance;
    }

    public double getTime() {
        return time;
    }
}
