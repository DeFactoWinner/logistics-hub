package com.winner.client.global.config;

import com.winner.client.global.security.FilterExceptionHandleFilter;
import com.winner.client.global.security.PreAuthenticatedHeaderFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@ConditionalOnWebApplication(type = Type.SERVLET)
public class SecurityConfig {

  private final PreAuthenticatedHeaderFilter preAuthenticatedHeaderFilter;
  private final FilterExceptionHandleFilter filterExceptionHandleFilter;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
        .csrf(AbstractHttpConfigurer::disable)
        .formLogin(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable)
        .sessionManagement(session ->
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(this::configureAuthorization)
        .addFilterBefore(preAuthenticatedHeaderFilter, UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(filterExceptionHandleFilter, preAuthenticatedHeaderFilter.getClass())
        .build();
  }

  private void configureAuthorization(AuthorizeHttpRequestsConfigurer
      <HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth) {
    auth
        .requestMatchers(this::isSkipAuth).permitAll()
        .anyRequest().authenticated();
  }

  private boolean isSkipAuth(HttpServletRequest request) {
    return "true".equalsIgnoreCase(request.getHeader("X-Auth-Skip"));
  }
}