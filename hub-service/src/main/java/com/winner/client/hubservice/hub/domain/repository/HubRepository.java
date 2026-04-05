package com.winner.client.hubservice.hub.domain.repository;

import com.winner.client.hubservice.hub.domain.entity.Hub;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HubRepository {

    Hub save(Hub hub);

    boolean existsByNameAndDeletedAtIsNull(String name);

    Optional<Hub> findByIdAndDeletedAtIsNull(UUID id);

    Page<Hub> findAllByDeletedAtIsNull(Pageable pageable);
}
