package com.yaohy.intelligentfarmingbackend.pojo.dto.result;

import lombok.Data;

@Data
public class EquipmentDetailResult {

    private Integer produceId;

    private Integer produceSkuId;

    private String produceName;

    private String produceImage;

    private Double producePrice;

    private Double freightPrice;

    private String produceDescription;

    private String producesSpecification;

}
