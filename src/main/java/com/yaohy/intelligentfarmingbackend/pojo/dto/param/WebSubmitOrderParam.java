package com.yaohy.intelligentfarmingbackend.pojo.dto.param;

import lombok.Data;

@Data
public class WebSubmitOrderParam {

    private Integer produceSkuId;

    private double producesWeight;

    private String address;

    private String orderRemark;

}
