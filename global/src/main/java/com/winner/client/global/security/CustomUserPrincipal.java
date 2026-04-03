package com.winner.client.global.security;

import java.util.UUID;

public record CustomUserPrincipal(UUID userId, String role, UUID referenceId) {

}
