package com.yaohy.intelligentfarmingbackend.pojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class UserRelation {

    @TableId(type = IdType.AUTO)
    private Integer fromId;
    private Integer toId;

}
