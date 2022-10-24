package com.yaohy.intelligentfarmingbackend.pojo.to.sensorService;

import lombok.Data;

@Data
public class SensorServiceEventResultTo<T> {

    private String name;
    private String type;
    private String senderId;
    private Long time;
    private T param;

}
