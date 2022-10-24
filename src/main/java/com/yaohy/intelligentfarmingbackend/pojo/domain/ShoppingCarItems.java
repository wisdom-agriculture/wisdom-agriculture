package com.yaohy.intelligentfarmingbackend.pojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class ShoppingCarItems {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer carId;

    private String shopName;

    private Integer producesId;

    private String producesName;

    private Double producesWeight;

    private String producesPer;

    private Double producesPrice;

    private Integer producesImage;

    private Integer producesSkuId;

}
