package com.yaohy.intelligentfarmingbackend.pojo.dto.result;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class GoodListResult {

    private List<GoodResult> goodResultList;

    @Data
    public static class GoodResult{

        private Integer reviewId;

        private String userName;

        private String userImage;

        private String content;

        private String reviewImage;

        private Date createTime;

    }

}
