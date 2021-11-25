package com.explorers.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author wugaobo
 */
@TableName("explorers_user_info")
@Data
public class UserInfo {

  @TableId(type = IdType.UUID)
  private String id;

  @TableField(value = "user_name")
  private String username;

  @TableField(value = "password")
  private String password;

  @TableField(exist = false)
  private String role = "ROLE_ADMIN";

  @TableField(value = "rememberme")
  private Integer rememberMe;
}
