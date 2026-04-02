package com.winner.client.hubservice.hub.presentation;

import com.winner.client.hubservice.hub.application.HubService;
import com.winner.client.hubservice.hub.application.dto.HubResult;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/v1/hubs")
public class InternalHubController {

    private final HubService hubService;

    @GetMapping("/{hubId}")
    public HubResult getHub(@PathVariable UUID hubId) {
        return hubService.getHub(hubId);
    }
}