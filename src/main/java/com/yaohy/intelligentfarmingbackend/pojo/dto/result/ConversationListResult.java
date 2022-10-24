package com.yaohy.intelligentfarmingbackend.pojo.dto.result;

import lombok.Data;

import java.util.List;

@Data
public class ConversationListResult{

    private List<ConversationResult> conversationResultList;

    @Data
    public static  class ConversationResult {

        private Integer userId;

        private Integer fromId;

        private String fromName;

        private String fromUrl;

        private Integer toId;

        private String toName;

        private String toUrl;

        private String type;

        private String content;

        private String imgUrl;

        private Long time;

    }

}
