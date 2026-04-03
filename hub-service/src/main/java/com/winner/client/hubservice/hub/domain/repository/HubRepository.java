package com.winner.client.hubservice.hub.domain.repository;

import com.winner.client.hubservice.hub.domain.entity.Hub;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HubRepository extends JpaRepository<Hub, UUID> {

    boolean existsByName(String name);
}
