package com.winner.client.hubservice.hub.infrastructure.repository;

import com.winner.client.hubservice.hub.domain.entity.HubRoute;
import com.winner.client.hubservice.hub.domain.repository.HubRouteRepository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaHubRouteRepository extends JpaRepository<HubRoute, UUID>, HubRouteRepository {

}
