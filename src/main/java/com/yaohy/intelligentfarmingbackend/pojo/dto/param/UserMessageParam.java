package com.yaohy.intelligentfarmingbackend.pojo.dto.param;

import lombok.Data;

@Data
public class UserMessageParam {

    private Integer toId;
//    private Integer typeId;
    private String content;

}
