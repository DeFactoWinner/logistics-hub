package com.winner.client.hubservice.hub.presentation;

import com.winner.client.hubservice.hub.application.HubService;
import com.winner.client.hubservice.hub.application.dto.CreateHubCommand;
import com.winner.client.hubservice.hub.application.dto.HubResult;
import com.winner.client.hubservice.hub.application.dto.UpdateHubCommand;
import com.winner.client.hubservice.hub.presentation.dto.CreateHubRequest;
import com.winner.client.hubservice.hub.presentation.dto.HubResponse;
import com.winner.client.hubservice.hub.presentation.dto.UpdateHubRequest;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hubs")
public class HubController {

    private final HubService hubService;

    @PostMapping
    public UUID createHub(@RequestBody CreateHubRequest request) {
        CreateHubCommand command = CreateHubCommand.builder()
            .name(request.getName())
            .address(request.getAddress())
            .lat(request.getLat())
            .lng(request.getLng())
            .build();

        return hubService.createHub(command);
    }

    @GetMapping("/{hubId}")
    public HubResponse getHub(@PathVariable UUID hubId) {
        HubResult result = hubService.getHub(hubId);

        return HubResponse.builder()
            .id(result.getId())
            .name(result.getName())
            .address(result.getAddress())
            .lat(result.getLat())
            .lng(result.getLng())
            .build();
    }

    @PatchMapping("/{hubId}")
    public void updateHub(@PathVariable UUID hubId, @RequestBody UpdateHubRequest request) {
        UpdateHubCommand command = new UpdateHubCommand(
            request.getName(),
            request.getAddress(),
            request.getLat(),
            request.getLng()
        );

        hubService.updateHub(hubId, command);
    }
}
