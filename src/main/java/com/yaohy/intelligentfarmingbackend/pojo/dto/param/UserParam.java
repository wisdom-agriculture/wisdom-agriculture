package com.yaohy.intelligentfarmingbackend.pojo.dto.param;

import lombok.Data;

@Data
public class UserParam {

    private Integer id;
    private String nickname;
    private String imgUrl;
    private String signature;
    private String farm;
    private String phone;
    private String password;
    private String code;

}
