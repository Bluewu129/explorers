package com.explorers.auth.filter;

import com.explorers.auth.domain.JwtUserInfo;
import com.explorers.model.UserInfo;
import com.explorers.utils.JwtTokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 自定义身份验证过滤器
 *
 * @author wugaobo
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

  private static final ThreadLocal<Integer> rememberMe = new ThreadLocal<>();
  private AuthenticationManager authenticationManager;

  public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {
    // 从输入流中获取到登录的信息
    try {
      UserInfo userInfo = new ObjectMapper().readValue(request.getInputStream(), UserInfo.class);
      rememberMe.set(userInfo.getRememberMe() == null ? 0 : userInfo.getRememberMe());
      return authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(userInfo.getUsername(), userInfo.getPassword(),
              new ArrayList<>())
      );
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 成功验证后调用的方法 /如果验证成功，就生成token并返回
   *
   * @param request
   * @param response
   * @param chain
   * @param authResult
   * @throws IOException
   * @throws ServletException
   */
  @Override
  protected void successfulAuthentication(HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain,
      Authentication authResult) throws IOException, ServletException {

    JwtUserInfo jwtUser = (JwtUserInfo) authResult.getPrincipal();
    log.info("jwtUser: {}", jwtUser.toString());
    boolean isRemember = rememberMe.get() == 1;

    String role = "";
    Collection<? extends GrantedAuthority> authorities = jwtUser.getAuthorities();
    for (GrantedAuthority authority : authorities) {
      role = authority.getAuthority();
    }

    String token = JwtTokenUtils.createToken(jwtUser.getUsername(), role, isRemember);
    // 返回创建成功的token
    // 但是这里创建的token只是单纯的token
    // 按照jwt的规定，最后请求的时候应该是 `Bearer token`
    response.setHeader("token", JwtTokenUtils.TOKEN_PREFIX + token);
  }

  @Override
  protected void unsuccessfulAuthentication(
      HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
      throws IOException, ServletException {
    response.getWriter().write("authentication failed, reason: " + failed.getMessage());
  }
}
