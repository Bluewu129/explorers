package com.explorers.auth.filter;

import com.explorers.auth.exception.TokenIsExpiredException;
import com.explorers.model.base.ResponseResult;
import com.explorers.utils.JwtTokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Collections;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * 自定义鉴权过滤器
 *
 * @author wugaobo
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

  public JwtAuthorizationFilter(
      AuthenticationManager authenticationManager) {
    super(authenticationManager);
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain) throws IOException, ServletException {
    String tokenHeader = request.getHeader(JwtTokenUtils.TOKEN_HEADER);

    // 如果请求头中没有Authorization
    if (tokenHeader == null || !tokenHeader.startsWith(JwtTokenUtils.TOKEN_PREFIX)) {
      chain.doFilter(request, response);
      return;
    }

    // 如果请求头中有token，则进行解析，并且设置认证信息
    try {
      SecurityContextHolder.getContext().setAuthentication(getAuthentication(tokenHeader));
    } catch (TokenIsExpiredException e) {
      response.setCharacterEncoding("UTF-8");
      response.setContentType("application/json; charset=utf-8");
      //返回json形式的错误信息
      response.setStatus(HttpServletResponse.SC_FORBIDDEN);
      String reason = "认证失败, 访问URL: " + request.getRequestURI();
      response.getWriter().write(new ObjectMapper().writeValueAsString(
          ResponseResult.systemError("Authentication failed", reason)));
      response.getWriter().flush();
      return;
    }
    super.doFilterInternal(request, response, chain);
  }


  /**
   * 这里从token中获取用户信息并新建一个token
   */
  private UsernamePasswordAuthenticationToken getAuthentication(String tokenHeader)
      throws TokenIsExpiredException {
    String token = tokenHeader.replace(JwtTokenUtils.TOKEN_PREFIX, "");
    boolean expiration = JwtTokenUtils.isExpiration(token);
    if (expiration) {
      throw new TokenIsExpiredException("token超时了");
    } else {
      String username = JwtTokenUtils.getUsername(token);
      String role = JwtTokenUtils.getUserRole(token);
      if (username != null) {
        return new UsernamePasswordAuthenticationToken(username, null,
            Collections.singleton(new SimpleGrantedAuthority(role))
        );
      }
    }
    return null;
  }
}
