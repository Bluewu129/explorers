package com.explorers.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.explorers.model.UserInfo;
import org.apache.ibatis.annotations.Select;

/**
 * UserMapper
 *
 * @author wugaobo
 */
public interface UserMapper extends BaseMapper<UserInfo> {

  /**
   * 根据用户名查询
   *
   * @param userName
   * @return
   */
  @Select("select id,user_name,password from explorers_user_info where user_name = #{userName}")
  UserInfo findByUserName(String userName);
}
