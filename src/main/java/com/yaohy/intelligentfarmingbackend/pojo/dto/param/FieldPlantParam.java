package com.yaohy.intelligentfarmingbackend.pojo.dto.param;

import lombok.Data;

@Data
public class FieldPlantParam {
    private Integer fieldId;
    private Integer plantId;
    private Integer plantStatusId;
    private Integer area;
    private String cropType;
    private String fertilizerName;
    private String plantingMethod;
    private String plantingYear;
    private String bagging;
    private String seedingPeriod;
    private String irrigationCycle;
    private String fertilizerCycle;
    private String weedingCycle;
    private String detailedAddress;
    private String remark;
}
