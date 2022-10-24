package com.yaohy.intelligentfarmingbackend.pojo.dto.result;

import lombok.Data;

import java.util.List;

@Data
public class ZoneCommentResult {
    private Integer id;
    private Integer userId;
    private String nickname;
    private String imgUrl;
    private String content;
    private Long time;
    private List<ZoneCommentResult> commentList;

}
