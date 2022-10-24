package com.yaohy.intelligentfarmingbackend.pojo.dto.result;

import lombok.Data;

@Data
public class MessageResult {

    private Integer id;

    private Integer fromId;

    private Integer toId;

    private String type;

    private String content;

    private String url;

    private Long time;



}
