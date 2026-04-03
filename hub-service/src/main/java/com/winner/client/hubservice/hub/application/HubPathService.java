package com.winner.client.hubservice.hub.application;

import com.winner.client.hubservice.hub.application.dto.HubRouteResult;
import com.winner.client.hubservice.hub.domain.graph.DijkstraPathFinder;
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

    public HubRoutePathResponse findShortestPathSteps(UUID from, UUID to) {

        HubGraph graph = graphBuilder.build();

        DijkstraPathFinder dijkstra = new DijkstraPathFinder();
        DijkstraPathFinder.Result result =
            dijkstra.findShortestPath(graph, from, to);

        List<HubNodeInfo> steps = new ArrayList<>();

        for (int i = 0; i < result.path.size() - 1; i++) {

            UUID fromHubId = result.path.get(i);
            UUID toHubId = result.path.get(i + 1);

            String fromName = hubRepository.findById(fromHubId)
                .map(h -> h.getName())
                .orElse("UNKNOWN");

            String toName = hubRepository.findById(toHubId)
                .map(h -> h.getName())
                .orElse("UNKNOWN");

            HubRouteResult route = hubRouteService
                .searchRoutes(fromHubId, toHubId)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Route not found"));

            steps.add(new HubNodeInfo(
                fromHubId,
                fromName,
                toHubId,
                toName,
                i + 1,
                route.getDistance(),
                route.getDuration()
            ));
        }

        return new HubRoutePathResponse(
            steps,
            steps.size()
        );
    }
}
