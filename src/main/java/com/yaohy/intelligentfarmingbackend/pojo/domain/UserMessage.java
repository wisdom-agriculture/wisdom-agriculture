package com.yaohy.intelligentfarmingbackend.pojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.models.auth.In;
import lombok.Data;

@Data
public class UserMessage {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer fromId;
    private Integer toId;
    private Integer typeId;
    private String content;
    private Long time;
    private Integer stick;
    private Integer disabled;
    private Integer state;
}
