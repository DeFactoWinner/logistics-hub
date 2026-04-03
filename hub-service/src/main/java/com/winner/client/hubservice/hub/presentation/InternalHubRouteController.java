package com.winner.client.hubservice.hub.presentation;

import com.winner.client.hubservice.hub.application.HubPathService;
import com.winner.client.hubservice.hub.application.HubRouteService;
import com.winner.client.hubservice.hub.application.dto.HubRouteResult;
import com.winner.client.hubservice.hub.presentation.dto.HubRoutePathResponse;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/v1/hub-routes")
public class InternalHubRouteController {

    private final HubRouteService hubRouteService;
    private final HubPathService hubPathService;

    @GetMapping
    public List<HubRouteResult> getRoutes(
        @RequestParam UUID fromHubId,
        @RequestParam UUID toHubId
    ) {
        return hubRouteService.searchRoutes(fromHubId, toHubId);
    }

    @GetMapping("/shortest")
    public ResponseEntity<HubRoutePathResponse> getShortestPath(
        @RequestParam UUID fromHubId,
        @RequestParam UUID toHubId
    ) {
        return ResponseEntity.ok(
            hubPathService.findShortestPathSteps(fromHubId, toHubId)
        );
    }
}