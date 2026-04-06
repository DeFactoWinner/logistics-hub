package com.winner.client.hubservice.hub.application;

import com.winner.client.hubservice.common.exception.hub.HubErrorCode;
import com.winner.client.hubservice.common.exception.hub.HubException;
import com.winner.client.hubservice.hub.application.dto.CreateHubCommand;
import com.winner.client.hubservice.hub.application.dto.HubResult;
import com.winner.client.hubservice.hub.application.dto.UpdateHubCommand;
import com.winner.client.hubservice.hub.application.port.UserPort;
import com.winner.client.hubservice.hub.domain.entity.Hub;
import com.winner.client.hubservice.hub.domain.repository.HubRepository;
import com.winner.client.hubservice.hub.domain.repository.HubRouteRepository;
import com.winner.client.hubservice.hub.domain.vo.HubLocation;
import com.winner.client.hubservice.hub.presentation.dto.HubPageResponse;
import com.winner.client.hubservice.hub.presentation.dto.HubResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HubService {

    private final HubRepository hubRepository;
    private final HubRouteRepository hubRouteRepository;
    private final UserPort userPort;

    @Caching(evict = {
        @CacheEvict(value = "hubs", allEntries = true)
    })
    @Transactional
    public UUID createHub(CreateHubCommand command) {
        HubLocation location = new HubLocation(
            command.getAddress(),
            command.getLat(),
            command.getLng()
        );

        if (hubRepository.existsByNameAndDeletedAtIsNull(command.getName())) {
            throw new HubException(HubErrorCode.DUPLICATE_HUB);
        }

        Hub hub = Hub.create(command.getName(), location);
        return hubRepository.save(hub).getId();
    }

    @Cacheable(value = "hub", key = "#hubId")
    public HubResult getHub(UUID hubId) {
        Hub hub = hubRepository.findByIdAndDeletedAtIsNull(hubId)
            .orElseThrow(()-> new HubException(HubErrorCode.HUB_NOT_FOUND));

        return HubResult.builder()
            .id(hub.getId())
            .name(hub.getName())
            .address(hub.getLocation().getAddress())
            .lat(hub.getLocation().getLat())
            .lng(hub.getLocation().getLng())
            .build();
    }

    @Caching(evict = {
        @CacheEvict(value = "hub", key = "#hubId"),
        @CacheEvict(value = "hubs", allEntries = true)
    })
    @Transactional
    public void updateHub(UUID hubId, UpdateHubCommand command) {
        Hub hub = hubRepository.findByIdAndDeletedAtIsNull(hubId)
            .orElseThrow(()-> new HubException(HubErrorCode.HUB_NOT_FOUND));

        if (!hub.getName().equals(command.getName()) &&
            hubRepository.existsByNameAndDeletedAtIsNull(command.getName())) {
            throw new HubException(HubErrorCode.DUPLICATE_HUB);
        }

        HubLocation location = new HubLocation(
            command.getAddress(),
            command.getLat(),
            command.getLng()
        );

        hub.update(command.getName(), location);
    }

    @Caching(evict = {
        @CacheEvict(value = "hub", key = "#hubId"),
        @CacheEvict(value = "hubs", allEntries = true)
    })
    @Transactional
    public void deleteHub(UUID hubId, UUID userId) {
        Hub hub = hubRepository.findByIdAndDeletedAtIsNull(hubId)
            .orElseThrow(()-> new HubException(HubErrorCode.HUB_NOT_FOUND));

        hub.delete(userId);

        var routes = hubRouteRepository
            .findAllByRouteInfo_FromHubIdOrRouteInfo_ToHubIdAndDeletedAtIsNull(hubId, hubId);

        for (var route : routes) {
            route.delete(userId);
        }
    }

    @Cacheable(value = "hubs", key = "#q + '-' + #pageable.pageNumber")
    public HubPageResponse searchHubs(String q, Pageable pageable) {

        long start = System.currentTimeMillis();

        Page<Hub> hubs;

        if (q == null || q.isBlank()) {
            hubs = hubRepository.findAllByDeletedAtIsNull(pageable);
        } else {
            hubs = hubRepository.findByNameContainingAndDeletedAtIsNull(q, pageable);
        }

        long end = System.currentTimeMillis();
        log.debug("DB 조회 시간: {} ms", (end - start));

        return new HubPageResponse(
            hubs.map(HubResponse::from).getContent(),
            hubs.getNumber(),
            hubs.getSize(),
            hubs.getTotalElements(),
            hubs.getTotalPages(),
            hubs.isLast()
        );
    }
}
