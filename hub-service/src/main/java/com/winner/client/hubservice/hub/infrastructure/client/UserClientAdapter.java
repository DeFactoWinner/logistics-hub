package com.winner.client.hubservice.hub.infrastructure.client;

import com.winner.client.hubservice.hub.application.port.UserPort;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserClientAdapter implements UserPort {

    private final UserClient userClient;

    @Override
    public void unassignUser(UUID userId) {
        userClient.unassignUser(userId);
    }
}
