package com.winner.client.hubservice.hub.application;

import com.winner.client.hubservice.hub.application.dto.CreateRouteCommand;
import com.winner.client.hubservice.hub.application.dto.HubRouteResult;
import com.winner.client.hubservice.hub.domain.entity.HubRoute;
import com.winner.client.hubservice.hub.domain.repository.HubRouteRepository;
import com.winner.client.hubservice.hub.domain.vo.Distance;
import com.winner.client.hubservice.hub.domain.vo.Duration;
import com.winner.client.hubservice.hub.domain.vo.RouteInfo;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HubRouteService {

    private final HubRouteRepository hubRouteRepository;

    @Transactional
    public UUID createRoute(CreateRouteCommand command) {
        RouteInfo routeInfo = new RouteInfo(
            command.getFromHubId(),
            command.getToHubId()
        );
        Distance distance = new Distance(command.getDistance());
        Duration duration = new Duration(command.getDuration());

        HubRoute route = HubRoute.create(routeInfo, distance, duration);

        return hubRouteRepository.save(route).getId();
    }

    @Transactional(readOnly = true)
    public List<HubRouteResult> getAllRoutes() {
        return hubRouteRepository.findAllByDeletedAtIsNull().stream()
            .map(route -> HubRouteResult.builder()
                .id(route.getId())
                .fromHubId(route.getRouteInfo().getFromHubId())
                .toHubId(route.getRouteInfo().getToHubId())
                .distance(route.getDistance().getValue())
                .duration(route.getDuration().getValue())
                .build())
            .toList();
    }

    @Transactional(readOnly = true)
    public List<HubRouteResult> searchRoutes(UUID fromHubId, UUID toHubId) {
        return hubRouteRepository.findByRouteInfo_FromHubIdAndRouteInfo_ToHubIdAndDeletedAtIsNull(fromHubId, toHubId).stream()
            .map(route -> HubRouteResult.builder()
                .id(route.getId())
                .fromHubId(route.getRouteInfo().getFromHubId())
                .toHubId(route.getRouteInfo().getToHubId())
                .distance(route.getDistance().getValue())
                .duration(route.getDuration().getValue())
                .build())
            .toList();
    }
}
