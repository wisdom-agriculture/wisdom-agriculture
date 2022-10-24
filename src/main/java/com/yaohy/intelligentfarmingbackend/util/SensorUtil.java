package com.yaohy.intelligentfarmingbackend.util;

import com.yaohy.intelligentfarmingbackend.pojo.to.SensorServiceApplyTo;
import com.yaohy.intelligentfarmingbackend.pojo.to.SleepApplyParamTo;
import com.yaohy.intelligentfarmingbackend.service.SensorService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;

@Component
public class SensorUtil {

    private SensorUtil(){};

    private SensorUtil sensorUtil;

    @Resource
    private SensorService sensorService;

    @PostConstruct
    public void init() {
        sensorUtil = this;
        sensorUtil.sensorService = this.sensorService;
    }

    public static Boolean start(Integer sensorId){
        Boolean result = false;



        return result;
    }

    public static Boolean stop(Integer sensorId){
        Boolean result = false;

        SensorServiceApplyTo<SleepApplyParamTo> sensorServiceApplyTo = new SensorServiceApplyTo();
        sensorServiceApplyTo.setName("Sleep");

        SleepApplyParamTo sleepParamTo = new SleepApplyParamTo();
        sleepParamTo.setStartTime(new Date().getTime());
        sleepParamTo.setStopTime(21474836470000L);

        //TODO send message

        return result;
    }

}
