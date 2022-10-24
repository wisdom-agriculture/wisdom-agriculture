package com.yaohy.intelligentfarmingbackend.pojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Field {

  @TableId(type = IdType.AUTO)
  private Integer id;
  private String name;
  private BigDecimal latitude;
  private BigDecimal longitude;
  private Integer area;
  private String location;
  private String description;
  private Integer userId;
  private Integer renterId;
  private Integer statusId;

}
