package com.yaohy.intelligentfarmingbackend.pojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Disease {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer fieldId;
    private String plant;
    private Long time;
    private Integer status;
    private String img;
    private String disease;

}
