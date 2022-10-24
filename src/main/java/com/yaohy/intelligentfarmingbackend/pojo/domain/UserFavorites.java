package com.yaohy.intelligentfarmingbackend.pojo.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class UserFavorites {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private Integer producesId;

    private String shopName;

    private String producesName;

    private String producesPer;

    private Integer producesImage;

    private Double producesPrice;

}
