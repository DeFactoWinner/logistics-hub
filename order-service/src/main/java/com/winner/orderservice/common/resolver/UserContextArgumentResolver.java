package com.winner.orderservice.common.resolver;

import com.winner.orderservice.common.UserContext;
import com.winner.orderservice.common.UserRole;
import java.util.UUID;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class UserContextArgumentResolver implements HandlerMethodArgumentResolver {

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.getParameterType().equals(UserContext.class);
  }

  @Override
  public Object resolveArgument(
      MethodParameter parameter,
      ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest,
      WebDataBinderFactory binderFactory) throws Exception {

    String userIdStr = webRequest.getHeader("X-User-Id");
    String roleStr = webRequest.getHeader("X-User-Role");
    String relationIdStr = webRequest.getHeader("X-Relation-Id");

    if (userIdStr == null || roleStr == null) {
      throw new IllegalArgumentException("User context details not found in headers");
    }

    UUID userId = UUID.fromString(userIdStr);
    UserRole role = UserRole.valueOf(roleStr);
    UUID relationId = relationIdStr != null ? UUID.fromString(relationIdStr) : null;

    return new UserContext(userId, role, relationId);
  }
}

