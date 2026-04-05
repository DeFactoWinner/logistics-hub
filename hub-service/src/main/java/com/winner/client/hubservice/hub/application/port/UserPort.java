package com.winner.client.hubservice.hub.application.port;

import java.util.UUID;

public interface UserPort {

    void unassignUser(UUID userId);
}
