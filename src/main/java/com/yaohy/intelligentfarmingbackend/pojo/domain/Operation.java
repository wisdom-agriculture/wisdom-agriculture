package com.yaohy.intelligentfarmingbackend.pojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Operation {

  @TableId(type = IdType.AUTO)
  private Integer id;
  private Integer fieldId;
  private String operator;
  private String phone;
  private Long time;
  private String content;
  private String remark;

}
