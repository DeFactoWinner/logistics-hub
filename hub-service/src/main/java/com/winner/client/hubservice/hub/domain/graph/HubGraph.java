package com.winner.client.hubservice.hub.domain.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class HubGraph {

    private final Map<UUID, List<HubEdge>> adjacencyList = new HashMap<>();

    public void addEdge(UUID from, HubEdge edge) {
        adjacencyList.computeIfAbsent(from, k -> new ArrayList<>()).add(edge);
    }

    public List<HubEdge> getEdges(UUID from) {
        return adjacencyList.getOrDefault(from, Collections.emptyList());
    }

    public Set<UUID> getNodes() {
        return adjacencyList.keySet();
    }
}
