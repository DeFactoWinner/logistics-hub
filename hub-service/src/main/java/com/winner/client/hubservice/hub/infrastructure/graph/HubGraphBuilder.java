package com.winner.client.hubservice.hub.infrastructure.graph;

import com.winner.client.hubservice.hub.domain.entity.HubRoute;
import com.winner.client.hubservice.hub.domain.graph.HubEdge;
import com.winner.client.hubservice.hub.domain.graph.HubGraph;
import com.winner.client.hubservice.hub.domain.repository.HubRouteRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HubGraphBuilder {

    private final HubRouteRepository hubRouteRepository;

    public HubGraph build() {
        List<HubRoute> routes = hubRouteRepository.findAll();

        HubGraph graph = new HubGraph();

        for (HubRoute route : routes) {
            var routeInfo = route.getRouteInfo();

            var from = routeInfo.getFromHubId();
            var to = routeInfo.getToHubId();

            double distance = route.getDistance().getValue();

            graph.addEdge(
                from,
                new HubEdge(to, distance)
            );

            graph.addEdge(
                to,
                new HubEdge(from, distance)
            );
        }

        return graph;
    }
}
