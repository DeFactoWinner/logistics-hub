package com.winner.client.global.pagination;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class PageRequestArgumentResolver implements HandlerMethodArgumentResolver {
  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return CommonPageRequest.class.equals(parameter.getParameterType());
  }

  @Override
  public Object resolveArgument(MethodParameter parameter,
      ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest,
      WebDataBinderFactory binderFactory) {
    int page = parseOrDefault(webRequest.getParameter("page"), CommonPageRequest.DEFAULT_PAGE);
    int size = parseOrDefault(webRequest.getParameter("size"), CommonPageRequest.DEFAULT_SIZE);
    String sort = parseOrDefault(webRequest.getParameter("sort"));
    return CommonPageRequest.of(page, size, sort);
  }

  private int parseOrDefault(String value, int defaultValue) {
    if (value == null) return defaultValue;
    try { return Integer.parseInt(value); }
    catch (NumberFormatException e) { return defaultValue; }
  }

  private String parseOrDefault(String value) {
    return (value == null || value.isBlank()) ? CommonPageRequest.DEFAULT_SORT : value;
  }
}