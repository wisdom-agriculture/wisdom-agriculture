package com.yaohy.intelligentfarmingbackend.pojo.dto.param.transport;

import lombok.Data;

@Data
public class ReplyParam {

    private Integer reviewId;

    private Integer toUserId;

    private String content;

}
