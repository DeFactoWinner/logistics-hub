package com.winner.client.global.config;

import com.winner.client.global.security.CustomUserPrincipal;
import feign.RequestInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
@ConditionalOnClass(RequestInterceptor.class)
public class FeignConfig {

  @Bean
  public RequestInterceptor requestInterceptor() {
    return requestTemplate -> {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

      if (authentication != null
          && authentication.getPrincipal() instanceof CustomUserPrincipal principal) {
        if (principal.userId() != null) {
          requestTemplate.header("X-User-Id", principal.userId().toString());
        }
        if (principal.role() != null) {
          requestTemplate.header("X-User-Role", principal.role());
        }
        if (principal.referenceId() != null) {
          requestTemplate.header("X-User-ReferenceId",
              principal.referenceId().toString());
        }
      }
    };
  }
}