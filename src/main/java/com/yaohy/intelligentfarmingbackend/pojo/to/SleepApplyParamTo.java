package com.yaohy.intelligentfarmingbackend.pojo.to;

import lombok.Data;

@Data
public class SleepApplyParamTo extends SensorServiceApplyParamTo {

    private Long startTime;
    private Long stopTime;

}
