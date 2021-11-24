package com.explorers.auth.domain;

import com.explorers.model.UserInfo;
import java.util.Collection;
import java.util.Collections;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 自定义用户信息
 *
 * @author wugaobo
 */
@Data
public class JwtUserInfo implements UserDetails {

  private UserInfo userInfo;
  private Collection<? extends GrantedAuthority> authorities;

  //自定义构造器
  public JwtUserInfo(UserInfo userInfo) {
    this.userInfo = userInfo;
    authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"));
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return userInfo.getPassword();
  }

  @Override
  public String getUsername() {
    return userInfo.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
