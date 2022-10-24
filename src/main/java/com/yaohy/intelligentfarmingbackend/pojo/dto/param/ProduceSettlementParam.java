package com.yaohy.intelligentfarmingbackend.pojo.dto.param;

import lombok.Data;

@Data
public class ProduceSettlementParam {

    private String shopName;

    private Integer produceId;

    private String produceName;

    private Double producePrice;

    private String producesPer;

    private Double produceWeight;

    private String freightPrice;

}
