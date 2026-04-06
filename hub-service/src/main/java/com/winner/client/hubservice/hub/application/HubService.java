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
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HubService {

    private final HubRepository hubRepository;
    private final HubRouteRepository hubRouteRepository;
    private final UserPort userPort;

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

    @Transactional
    public void deleteHub(UUID hubId, UUID userId) {
        Hub hub = hubRepository.findByIdAndDeletedAtIsNull(hubId)
            .orElseThrow(()-> new HubException(HubErrorCode.HUB_NOT_FOUND));

        userPort.unassignUsersByReferenceId(hubId);

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
        System.out.println("DB 조회 시간 " + (end - start) + " ms");

        return new HubPageResponse(
            hubs.map(h -> HubResponse.builder()
                .id(h.getId())
                .name(h.getName())
                .address(h.getLocation().getAddress())
                .lat(h.getLocation().getLat())
                .lng(h.getLocation().getLng())
                .build()
            ).getContent(),
            hubs.getNumber(),
            hubs.getSize(),
            hubs.getTotalElements(),
            hubs.getTotalPages(),
            hubs.isLast()
        );
    }
}
