package com.yaohy.intelligentfarmingbackend.pojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class User {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String nickname;
    private String signature;
    private String imgUrl;
    private String farm;
    private String phone;
    private String password;
    private Integer deptId;
}
