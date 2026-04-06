package com.winner.client.hubservice.hub.presentation;

import com.winner.client.global.security.CustomUserPrincipal;
import com.winner.client.hubservice.hub.application.HubService;
import com.winner.client.hubservice.hub.application.dto.CreateHubCommand;
import com.winner.client.hubservice.hub.application.dto.HubResult;
import com.winner.client.hubservice.hub.application.dto.UpdateHubCommand;
import com.winner.client.hubservice.hub.presentation.dto.CreateHubRequest;
import com.winner.client.hubservice.hub.presentation.dto.HubResponse;
import com.winner.client.hubservice.hub.presentation.dto.UpdateHubRequest;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hubs")
public class HubController {

    private final HubService hubService;

    @PostMapping
    public UUID createHub(@RequestBody CreateHubRequest request) {
        CreateHubCommand command = CreateHubCommand.from(request);

        return hubService.createHub(command);
    }

    @GetMapping("/{hubId}")
    public HubResponse getHub(@PathVariable UUID hubId) {
        HubResult result = hubService.getHub(hubId);

        return HubResponse.from(result);
    }

    @PatchMapping("/{hubId}")
    public void updateHub(@PathVariable UUID hubId, @RequestBody UpdateHubRequest request) {
        UpdateHubCommand command = UpdateHubCommand.from(request);

        hubService.updateHub(hubId, command);
    }

    @DeleteMapping("/{hubId}")
    public void deleteHub(@PathVariable UUID hubId, @AuthenticationPrincipal CustomUserPrincipal user) {
        hubService.deleteHub(hubId, user.userId());
    }

    @GetMapping
    public Page<HubResponse> searchHubs(
            @RequestParam(required = false) String q,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return hubService.searchHubs(q, pageable)
            .map(HubResponse::from);
    }
}
