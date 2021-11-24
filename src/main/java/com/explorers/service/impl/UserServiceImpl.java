package com.explorers.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.explorers.mapper.UserMapper;
import com.explorers.model.UserInfo;
import com.explorers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wugaobo
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserInfo> implements UserService {

  @Autowired
  private UserMapper userMapper;

  @Override
  public UserInfo findByUserName(String name) {
    return userMapper.findByUserName(name);
  }
}
