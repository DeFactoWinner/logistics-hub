package com.winner.client.hubservice.hub.application;

import com.winner.client.hubservice.hub.domain.graph.DijkstraPathFinder;
import com.winner.client.hubservice.hub.domain.graph.HubGraph;
import com.winner.client.hubservice.hub.domain.repository.HubRepository;
import com.winner.client.hubservice.hub.infrastructure.graph.HubGraphBuilder;
import com.winner.client.hubservice.hub.presentation.dto.HubNodeResponse;
import com.winner.client.hubservice.hub.presentation.dto.ShortestPathResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HubPathService {

    private final HubGraphBuilder graphBuilder;
    private final HubRepository hubRepository;

    public ShortestPathResponse findShortestPath(UUID from, UUID to) {

        HubGraph graph = graphBuilder.build();

        DijkstraPathFinder dijkstra = new DijkstraPathFinder();
        DijkstraPathFinder.Result result =
            dijkstra.findShortestPath(graph, from, to);

        List<HubNodeResponse> nodes = new ArrayList<>();

        int sequence = 1;

        for (UUID hubId : result.path) {

            String hubName = hubRepository.findById(hubId)
                .map(hub -> hub.getName())
                .orElse("UNKNOWN");

            nodes.add(new HubNodeResponse(
                hubId,
                hubName,
                sequence++
            ));
        }

        return new ShortestPathResponse(
            nodes.size(),
            nodes
        );
    }
}
