package com.yaohy.intelligentfarmingbackend.pojo.dto.result;

import lombok.Data;

import java.util.List;

@Data
public class UserMessageListResult {

    private List<UserMessageResult> userMessageResults;
    private Integer count;
    @Data
    public static class UserMessageResult{

        private Integer id;
        private Integer currentUserId;
        private String imgUrl;
        private Integer userId;
        private String nickname;
        private String content;
        private Integer type;
        private Long time;
        private Integer stick;
        private Integer disabled;
    }

}
