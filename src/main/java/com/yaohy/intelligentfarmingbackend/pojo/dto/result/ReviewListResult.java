package com.yaohy.intelligentfarmingbackend.pojo.dto.result;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ReviewListResult {



    private List<Review> reviewList;

    @Data
    public static class Review{

        private Integer reviewId;

        private String userName;

        private String userImage;

        private String content;

        private String reviewImage;

        private Date createTime;

    }

}
