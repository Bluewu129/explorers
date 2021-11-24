package com.explorers.auth.exception;

import com.explorers.model.base.ResponseResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * 认证失败处理类
 *
 * @author wugaobo
 */
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException) throws IOException, ServletException {

    response.setCharacterEncoding("UTF-8");
    response.setContentType("application/json; charset=utf-8");
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    String reason = "认证失败:" + authException.getMessage() + ", 访问URL: " + request.getRequestURI();
    response.getWriter().write(new ObjectMapper().writeValueAsString(
        ResponseResult.systemError("Authentication failed", reason)));
  }
}
