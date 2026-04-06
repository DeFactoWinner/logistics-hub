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
import java.util.UUID;
import lombok.RequiredArgsConstructor;
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
}
