package com.yaohy.intelligentfarmingbackend.pojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class OrderItems {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer orderId;

    private Integer producesId;

    private String producesName;

    private String producesPer;

    private Double producesWeight;

    private Double producesPrice;

    private Integer producesImage;

    private String shopName;

    private Double freightPrice;
}
