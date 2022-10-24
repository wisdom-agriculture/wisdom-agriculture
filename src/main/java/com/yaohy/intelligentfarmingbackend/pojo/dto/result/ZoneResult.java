package com.yaohy.intelligentfarmingbackend.pojo.dto.result;

import lombok.Data;

@Data
public class ZoneResult {

    private Integer id;
    private Integer userId;
    private String nickname;
    private String imgUrl;
    private Boolean isSubscribed;
    private String content;
    private Long time;
    private Boolean isLike;
    private Integer likeNum;
    private Integer commentNum;
    //TODO 分享数

    private String[] imgs;

}
