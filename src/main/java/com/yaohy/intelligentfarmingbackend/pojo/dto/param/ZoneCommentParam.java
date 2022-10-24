package com.yaohy.intelligentfarmingbackend.pojo.dto.param;

import lombok.Data;

@Data
public class ZoneCommentParam {

    private Integer zoneId;
    private Integer parentId;
    private String content;

}
