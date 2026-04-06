package com.winner.client.hubservice.hub.presentation;

import com.winner.client.hubservice.hub.application.HubRouteService;
import com.winner.client.hubservice.hub.application.dto.CreateRouteCommand;
import com.winner.client.hubservice.hub.application.dto.HubRouteResult;
import com.winner.client.hubservice.hub.presentation.dto.CreateHubRouteRequest;
import com.winner.client.hubservice.hub.presentation.dto.HubRouteResponse;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hub-routes")
public class HubRouteController {

    private final HubRouteService hubRouteService;

    @PostMapping
    public UUID createHubRoute(@RequestBody CreateHubRouteRequest request) {
        CreateRouteCommand command = CreateRouteCommand.from(request);

        return hubRouteService.createRoute(command);
    }

    @GetMapping
    public List<HubRouteResponse> getRoutes(
        @RequestParam(required = false) UUID fromHubId,
        @RequestParam(required = false) UUID toHubId
    ) {

        List<HubRouteResult> results;

        if (fromHubId != null && toHubId != null) {
            results = hubRouteService.searchRoutes(fromHubId, toHubId);
        } else {
            results = hubRouteService.getAllRoutes();
        }

        return results.stream()
            .map(HubRouteResponse::from)
            .toList();
    }

}
