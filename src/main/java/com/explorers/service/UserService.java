package com.explorers.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.explorers.model.UserInfo;

/**
 * @author wugaobo
 */
public interface UserService extends IService<UserInfo> {

  /**
   * 根据username查找用户
   *
   * @param name
   * @return
   */
  UserInfo findByUserName(String name);
}
