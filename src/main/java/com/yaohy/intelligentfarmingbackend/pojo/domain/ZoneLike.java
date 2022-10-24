package com.yaohy.intelligentfarmingbackend.pojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class ZoneLike {

    @TableId(type = IdType.AUTO)
    private Integer zoneId;
    private Integer fromId;

}
