package com.yaohy.intelligentfarmingbackend.pojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class ProducesImages {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer produceId;

    private String imageAddress;

    private Integer imageOrder;
}
