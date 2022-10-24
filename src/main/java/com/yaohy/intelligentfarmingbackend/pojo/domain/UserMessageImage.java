package com.yaohy.intelligentfarmingbackend.pojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class UserMessageImage {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userMessageId;
    private String url;
}
