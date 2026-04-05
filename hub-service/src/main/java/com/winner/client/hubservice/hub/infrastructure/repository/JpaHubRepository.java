package com.winner.client.hubservice.hub.infrastructure.repository;

import com.winner.client.hubservice.hub.domain.entity.Hub;
import com.winner.client.hubservice.hub.domain.repository.HubRepository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaHubRepository extends JpaRepository<Hub, UUID>, HubRepository {

}
