package com.yaohy.intelligentfarmingbackend.pojo.to;

import lombok.Data;

@Data
public class  SensorServiceApplyTo<T extends SensorServiceApplyParamTo> {

    private String name;
    private T param;

}
