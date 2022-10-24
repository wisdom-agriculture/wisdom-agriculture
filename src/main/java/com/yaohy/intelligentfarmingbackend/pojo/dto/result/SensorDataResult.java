package com.yaohy.intelligentfarmingbackend.pojo.dto.result;

import lombok.Data;

@Data
public class SensorDataResult {

    private Integer id;
    private Integer sensorId;
    private Object content;
    private Long time;

}
