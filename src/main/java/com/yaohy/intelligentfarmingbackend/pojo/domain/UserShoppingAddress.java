package com.yaohy.intelligentfarmingbackend.pojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class UserShoppingAddress {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private String userName;

    private String userPhone;

    private String address;

    private Integer defaultAddress;

}
