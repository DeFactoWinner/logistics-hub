package com.winner.client.hubservice.hub.domain.repository;

import com.winner.client.hubservice.hub.domain.entity.Hub;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HubRepository extends JpaRepository<Hub, UUID> {

    boolean existsByNameAndDeletedAtIsNull(String name);

    Optional<Hub> findByIdAndDeletedAtIsNull(UUID id);

    Page<Hub> findAllByDeletedAtIsNull(Pageable pageable);
}
