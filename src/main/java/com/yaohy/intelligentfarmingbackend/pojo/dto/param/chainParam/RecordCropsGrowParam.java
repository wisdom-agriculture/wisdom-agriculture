package com.yaohy.intelligentfarmingbackend.pojo.dto.param.chainParam;

import lombok.Data;

/**
 * @author Valentine
 */
@Data
public class RecordCropsGrowParam {
    private Long cropsId;
    private String temperature;
    private String growthSituation;
    private String lightConditions;
    private String moisture;
    private String remark;
    private String url;
}
