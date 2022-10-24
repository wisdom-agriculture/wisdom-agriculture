package com.yaohy.intelligentfarmingbackend.config.mqtt;

import com.yaohy.intelligentfarmingbackend.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Configuration
@EnableScheduling
public class SensorDataScheduleTask {

    @Autowired
    private RedisUtil redisUtil;

//    @Scheduled(cron = "0 0/1 * * * ? ")//every minute
////    @Scheduled(cron = "0/1 * * * * ? ")//every second
//    private void gatherSensorDataA(){
//        System.out.println("------1sec----start------fromA");
//        System.out.println("------1min----start------fromA");
////        Object mqttMessage = redisUtil.rPop("mqtt_message");
//
//        List<Object> mqtt_message = redisUtil.rPopAll("mqtt_message");
//
//    }

//    @Scheduled(cron = "0/1 * * * * ? ")//every second
//    private void gatherSensorDataB(){
//        System.out.println("------1sec----start------fromB");
//        Object mqttMessage = redisUtil.rPop("mqtt_message");
//        System.out.println(mqttMessage);
//    }

}
