package com.winner.client.hubservice.hub.application;

import com.winner.client.hubservice.hub.domain.graph.DijkstraPathFinder;
import com.winner.client.hubservice.hub.domain.graph.HubGraph;
import com.winner.client.hubservice.hub.infrastructure.graph.HubGraphBuilder;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HubPathService {

    private final HubGraphBuilder graphBuilder;

    public DijkstraPathFinder.Result findShortestPath(UUID from, UUID to) {

        HubGraph graph = graphBuilder.build();

        DijkstraPathFinder dijkstra = new DijkstraPathFinder();

        return dijkstra.findShortestPath(graph, from, to);
    }
}
