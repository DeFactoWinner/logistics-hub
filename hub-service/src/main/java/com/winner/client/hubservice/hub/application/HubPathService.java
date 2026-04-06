package com.winner.client.hubservice.hub.application;

import com.winner.client.hubservice.hub.domain.graph.DijkstraPathFinder;
import com.winner.client.hubservice.hub.domain.graph.HubEdge;
import com.winner.client.hubservice.hub.domain.graph.HubGraph;
import com.winner.client.hubservice.hub.domain.repository.HubRepository;
import com.winner.client.hubservice.hub.infrastructure.graph.HubGraphBuilder;
import com.winner.client.hubservice.hub.presentation.dto.HubNodeInfo;
import com.winner.client.hubservice.hub.presentation.dto.HubNodeResponse;
import com.winner.client.hubservice.hub.presentation.dto.HubRoutePathResponse;
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
    private final HubRouteService hubRouteService;

    public ShortestPathResponse findShortestPath(UUID from, UUID to) {

        HubGraph graph = graphBuilder.build();

        DijkstraPathFinder dijkstra = new DijkstraPathFinder();
        DijkstraPathFinder.Result result =
            dijkstra.findShortestPath(graph, from, to);

        List<HubNodeResponse> nodes = new ArrayList<>();

        int sequence = 1;

        for (UUID hubId : result.path) {

            String hubName = hubRepository.findByIdAndDeletedAtIsNull(hubId)
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
            nodes,
            result.totalTime
        );
    }

    public HubRoutePathResponse findShortestPathSteps(UUID from, UUID to) {

        HubGraph graph = graphBuilder.build();

        DijkstraPathFinder dijkstra = new DijkstraPathFinder();
        DijkstraPathFinder.Result result =
            dijkstra.findShortestPath(graph, from, to);

        List<HubNodeInfo> steps = new ArrayList<>();

        for (int i = 0; i < result.path.size() - 1; i++) {

            UUID fromHubId = result.path.get(i);
            UUID toHubId = result.path.get(i + 1);

            String fromName = hubRepository.findByIdAndDeletedAtIsNull(fromHubId)
                .map(h -> h.getName())
                .orElse("UNKNOWN");

            String toName = hubRepository.findByIdAndDeletedAtIsNull(toHubId)
                .map(h -> h.getName())
                .orElse("UNKNOWN");

            HubEdge edge = graph.getEdges(fromHubId).stream()
                .filter(e -> e.getTo().equals(toHubId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Edge not found"));

            steps.add(new HubNodeInfo(
                fromHubId,
                fromName,
                toHubId,
                toName,
                i + 1,
                edge.getDistance(),
                edge.getTime()
            ));
        }

        return new HubRoutePathResponse(
            steps,
            steps.size(),
            result.totalTime
        );
    }
}
