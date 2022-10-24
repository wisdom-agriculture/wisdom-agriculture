package com.yaohy.intelligentfarmingbackend.pojo.dto.result;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ReviewDetailResult {

    private String userName;

    private String userImage;

    private String content;

    private Integer goodNumber;

    private Integer reviewNumber;

    private Integer forwardNumber;

    private List<String> reviewImages;

    private List<OtherReview> otherReviewList;

    private Integer isGood;

    @Data
    public static class OtherReview{

        private Integer fromUserId;

        private String fromUserName;

        private String fromUserImage;

        private Integer toUserId;

        private String toUserName;

        private String toUserImage;

        private String content;

        private Date createTime;

    }

}
