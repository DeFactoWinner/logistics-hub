package com.winner.client.hubservice.hub.domain.repository;

import com.winner.client.hubservice.hub.domain.entity.HubRoute;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HubRouteRepository extends JpaRepository<HubRoute, UUID> {

    List<HubRoute> findByRouteInfo_FromHubIdAndRouteInfo_ToHubId(
        UUID fromHubId,
        UUID toHubId
    );
}
