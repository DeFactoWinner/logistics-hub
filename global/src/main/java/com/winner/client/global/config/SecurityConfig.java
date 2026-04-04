package com.winner.client.global.config;

import com.winner.client.global.security.FilterExceptionHandleFilter;
import com.winner.client.global.security.PreAuthenticatedHeaderFilter;
import com.winner.client.global.variable.AuthProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableConfigurationProperties(AuthProperties.class)
@ConditionalOnWebApplication(type = Type.SERVLET)
public class SecurityConfig {

  private final PreAuthenticatedHeaderFilter preAuthenticatedHeaderFilter;
  private final FilterExceptionHandleFilter filterExceptionHandleFilter;
  private final AuthProperties authProperties;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    String[] whiteList = (authProperties.getWhiteList() != null)
        ? authProperties.getWhiteList().toArray(new String[0])
        : new String[0];

    return http
        .csrf(AbstractHttpConfigurer::disable)
        .formLogin(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable)
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(whiteList).permitAll()
            .anyRequest().authenticated()
        )
        .addFilterBefore(preAuthenticatedHeaderFilter, UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(filterExceptionHandleFilter, preAuthenticatedHeaderFilter.getClass())
        .build();
  }
}