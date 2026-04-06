package com.winner.client.global.security;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("auditorAwareImpl")
public class AuditorAwareImpl implements AuditorAware<UUID> {

  private static final UUID SYSTEM_ID = UUID.fromString("00000000-0000-0000-0000-000000000000");

  @Override
  public Optional<UUID> getCurrentAuditor() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null ||
        !authentication.isAuthenticated() ||
        authentication instanceof AnonymousAuthenticationToken) {
      return Optional.of(SYSTEM_ID);
    }
    try {
      CustomUserPrincipal user = (CustomUserPrincipal) authentication.getPrincipal();
      return Optional.ofNullable(user.userId());
    } catch (ClassCastException e) {
      return Optional.of(SYSTEM_ID);
    }
  }
}
