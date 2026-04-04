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

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    String userIdStr = request.getHeader("X-User-Id");
    String userRole = request.getHeader("X-User-Role");
    Object referenceIdObj = request.getHeader("X-User-ReferenceId");

    if (userIdStr == null || userIdStr.isEmpty()) {
      throw new BusinessException(CommonErrorCode.UNAUTHORIZED);
    }

    try {
      UUID userId = UUID.fromString(userIdStr);
      UUID referenceId;

      try {
        referenceId = UUID.fromString(referenceIdObj.toString());
      } catch (IllegalArgumentException e) {
        referenceId = null;
      }
      CustomUserPrincipal customUserPrincipal =
          new CustomUserPrincipal(userId, userRole, referenceId);

      List<SimpleGrantedAuthority> authorities = Collections.emptyList();
      if (userRole != null && !userRole.isEmpty()) {
        authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + userRole));
      }
      UsernamePasswordAuthenticationToken authentication =
          new UsernamePasswordAuthenticationToken(customUserPrincipal, null, authorities);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    } catch (IllegalArgumentException e) {
      throw new BusinessException(CommonErrorCode.UNAUTHORIZED);
    }
    filterChain.doFilter(request, response);
  }
}