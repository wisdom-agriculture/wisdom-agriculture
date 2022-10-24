package com.yaohy.intelligentfarmingbackend.pojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Sensor {

  @TableId(type = IdType.AUTO)
  private Integer id;
  private Integer typeId;
  private String objectId;
  private Integer userId;

}
