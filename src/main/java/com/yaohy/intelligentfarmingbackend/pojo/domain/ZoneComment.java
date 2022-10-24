package com.yaohy.intelligentfarmingbackend.pojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class ZoneComment {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer zoneId;
    private Integer fromId;
    private Integer parentId;
    private String content;
    private Long time;

}
