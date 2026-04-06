package com.winner.client.hubservice.config;

import com.winner.client.global.security.CustomUserPrincipal;
import java.util.Optional;
import java.util.UUID;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class AuditorConfig {

    @Bean
    public AuditorAware<UUID> auditorProvider() {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null) {
                return Optional.empty();
            }

            Object principal = authentication.getPrincipal();

            if (!(principal instanceof CustomUserPrincipal)) {
                return Optional.empty();
            }

            CustomUserPrincipal user = (CustomUserPrincipal) principal;
            return Optional.of(user.userId());
        };
    }
}