package com.yaohy.intelligentfarmingbackend.pojo.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class UserFieldRelation {

  @TableId
  private Integer userId;
  @TableField
  private Integer fieldId;

}
