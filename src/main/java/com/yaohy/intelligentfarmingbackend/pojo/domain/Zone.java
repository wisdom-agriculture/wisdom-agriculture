package com.yaohy.intelligentfarmingbackend.pojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Zone {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private String content;
    private Long time;

}
