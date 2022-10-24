package com.yaohy.intelligentfarmingbackend.pojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class ProducesSku {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer produceId;

    private Double transactionPrice;

    private Double producesPrice;

    private Double producesWeight;

    private String producesPer;

    private String producesSpecification;

    private Double freightPrice;
}
