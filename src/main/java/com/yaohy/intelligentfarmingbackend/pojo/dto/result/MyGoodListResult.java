package com.yaohy.intelligentfarmingbackend.pojo.dto.result;

import lombok.Data;

import java.util.List;

@Data
public class MyGoodListResult {

    private List<MyGood> myGoodList;

    @Data
    public static class MyGood{

        private Integer reviewId;

        private Integer userId;

        private String userName;

        private String userImage;

        private String content;

        private Integer goodNumber;

        private Integer replyNumber;

        private Integer forwardNumber;
    }

}
