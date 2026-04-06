package com.winner.client.hubservice.hub.domain.repository;

import com.winner.client.hubservice.hub.domain.entity.HubRoute;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HubRouteRepository {

    HubRoute save(HubRoute route);

    List<HubRoute> findAll();

    List<HubRoute> findByRouteInfo_FromHubIdAndRouteInfo_ToHubIdAndDeletedAtIsNull(
        UUID fromHubId,
        UUID toHubId
    );

    Optional<HubRoute> findByIdAndDeletedAtIsNull(UUID id);

    List<HubRoute> findAllByDeletedAtIsNull();

    List<HubRoute> findAllByRouteInfo_FromHubIdOrRouteInfo_ToHubIdAndDeletedAtIsNull(
        UUID fromHubId,
        UUID toHubId
    );
}
