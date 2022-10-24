package com.yaohy.intelligentfarmingbackend.pojo.dto.result;

import lombok.Data;

@Data
public class UserFanResult {

    private Integer id;
    private String nickname;
    private String signature;
    private String imgUrl;
    private String farm;
    private String phone;
    private Boolean isMutual;

}
