package com.explorers.auth.serivce;

import com.explorers.auth.domain.JwtUserInfo;
import com.explorers.model.UserInfo;
import com.explorers.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 自定义登陆校验
 *
 * @author wugaobo
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

  @Autowired
  private UserService userService;

  @Override
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    //根据userName获取用户
    UserInfo userInfo = userService.findByUserName(s);
    if (userInfo == null) {
      log.info("Username notfound: {}.", s);
      throw new UsernameNotFoundException("Username notfound：{}." + s);
    }
    return new JwtUserInfo(userInfo);
  }
}
