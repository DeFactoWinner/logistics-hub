package com.winner.client.hubservice.hub.application;

import com.winner.client.hubservice.hub.domain.graph.DijkstraPathFinder;
import com.winner.client.hubservice.hub.domain.graph.HubGraph;
import com.winner.client.hubservice.hub.infrastructure.graph.HubGraphBuilder;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HubPathService {

    private final HubGraphBuilder graphBuilder;

    public double findShortestDistance(UUID from, UUID to) {

        HubGraph graph = graphBuilder.build();

        DijkstraPathFinder dijkstra = new DijkstraPathFinder();
        Map<UUID, Double> distances = dijkstra.findShortestDistances(graph, from);

        return distances.getOrDefault(to, -1.0);
    }
}
