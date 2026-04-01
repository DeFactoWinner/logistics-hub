package com.winner.client.hubservice.hub.presentation;

import com.winner.client.hubservice.hub.application.HubRouteService;
import com.winner.client.hubservice.hub.application.dto.HubRouteResult;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/v1/hub-routes")
public class InternalHubRouteController {

    private final HubRouteService hubRouteService;

    @GetMapping
    public List<HubRouteResult> getRoutes(
        @RequestParam UUID fromHubId,
        @RequestParam UUID toHubId
    ) {
        return hubRouteService.searchRoutes(fromHubId, toHubId);
    }
}