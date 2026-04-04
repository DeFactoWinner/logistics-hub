package com.winner.client.global.security;

import com.winner.client.global.exception.BusinessException;
import com.winner.client.global.exception.CommonErrorCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
public class PreAuthenticatedHeaderFilter extends OncePerRequestFilter {

  private static UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(
      UUID userId, String userRole, UUID referenceId) {
    CustomUserPrincipal customUserPrincipal =
        new CustomUserPrincipal(userId, userRole, referenceId);

    List<SimpleGrantedAuthority> authorities = Collections.emptyList();
    if (userRole != null && !userRole.isEmpty()) {
      String roleWithPrefix = "ROLE_" + userRole;
      authorities = Collections.singletonList(new SimpleGrantedAuthority(roleWithPrefix));
    }
    return new UsernamePasswordAuthenticationToken(customUserPrincipal, null, authorities);
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    String userIdStr = request.getHeader("X-User-Id");
    String userRole = request.getHeader("X-User-Role");
    String referenceIdStr = request.getHeader("X-User-ReferenceId");
    String skipAuth = request.getHeader("X-Auth-Skip");

    log.info("[Security 필터] 요청 감지 - Path: {}", request);
    if ("true".equalsIgnoreCase(skipAuth)) {
      filterChain.doFilter(request, response);
      return;
    }

    if (userIdStr == null || userIdStr.isEmpty()) {
      throw new BusinessException(CommonErrorCode.UNAUTHORIZED);
    }

    try {
      UUID userId = UUID.fromString(userIdStr);
      UUID referenceId = null;

      if (referenceIdStr != null && !referenceIdStr.isEmpty() && !"null".equals(referenceIdStr)) {
        try {
          referenceId = UUID.fromString(referenceIdStr);
        } catch (IllegalArgumentException ignored) {
        }
      }
      UsernamePasswordAuthenticationToken authentication = getUsernamePasswordAuthenticationToken(
          userId, userRole, referenceId);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    } catch (Exception e) {
      throw new BusinessException(CommonErrorCode.UNAUTHORIZED);
    }
    filterChain.doFilter(request, response);
  }
}