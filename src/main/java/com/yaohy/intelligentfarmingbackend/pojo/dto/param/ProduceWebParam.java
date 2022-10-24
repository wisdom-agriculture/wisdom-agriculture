package com.yaohy.intelligentfarmingbackend.pojo.dto.param;

import lombok.Data;

@Data
public class ProduceWebParam {

    private String producesName;

    private Double producesPrice;

    private String producesPer;

    private String shoppingAddress;

    private String userPhone;

    private Integer producesCategory;

    private String producesDescription;
}
