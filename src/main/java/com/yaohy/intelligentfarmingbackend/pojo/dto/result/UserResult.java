package com.yaohy.intelligentfarmingbackend.pojo.dto.result;

import lombok.Data;

@Data
public class UserResult {

    private Integer id;
    private String nickname;
    private String signature;
    private String imgUrl;
    private String farm;
    private String phone;

    private Integer subscribeNum;
    private Integer fanNum;
    private Integer getLikeNum;

    private Integer usagePercentage;
    private Integer areaSum;
    private Integer areaNowSum;
    private Integer plantAccount;

}
