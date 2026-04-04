package com.winner.client.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.winner.client.global.exception.BusinessException;
import com.winner.client.global.exception.CommonErrorCode;
import com.winner.client.global.response.ApiResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class FilterExceptionHandleFilter extends OncePerRequestFilter {

  private final ObjectMapper objectMapper;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain)
      throws IOException {
    try {
      chain.doFilter(request, response);
    } catch (BusinessException e) {
      setErrorResponse(response, e);
    } catch (Exception e) {
      setErrorResponse(response, new BusinessException(CommonErrorCode.INTERNAL_SERVER_ERROR));
    }
  }

  private void setErrorResponse(HttpServletResponse response, BusinessException e)
      throws IOException {
    response.setStatus(e.getErrorCode().getStatus().value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding("UTF-8");

    ApiResponse<Void> errorResponse = ApiResponse.error(e.getErrorCode());

    String json = objectMapper.writeValueAsString(errorResponse);
    response.getWriter().write(json);
  }
}
