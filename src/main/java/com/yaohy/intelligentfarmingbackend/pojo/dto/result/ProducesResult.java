package com.yaohy.intelligentfarmingbackend.pojo.dto.result;

import lombok.Data;

@Data
public class ProducesResult {


    private Integer id;

    private String producesName;

    private Double producesPrice;

    private String producesPer;

    private Double transactionPrice;

    private String producesImage;

    private Integer producesCategory;
}
