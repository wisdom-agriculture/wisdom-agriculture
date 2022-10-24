package com.yaohy.intelligentfarmingbackend.pojo.dto.param;

import lombok.Data;

@Data
public class OperationRecordParam {

    private Integer operationId;
    private Integer fieldId;
    private String description;

}
