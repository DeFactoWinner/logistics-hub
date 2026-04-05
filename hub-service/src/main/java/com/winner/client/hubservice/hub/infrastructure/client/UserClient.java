package com.winner.client.hubservice.hub.infrastructure.client;

import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="user-service")
public interface UserClient {

    @PatchMapping("/internal/v1/users/{userId}/unassign")
    void unassignUser(@PathVariable UUID userId);
}
