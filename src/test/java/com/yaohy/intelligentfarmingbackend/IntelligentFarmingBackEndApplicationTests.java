package com.yaohy.intelligentfarmingbackend;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.yaohy.intelligentfarmingbackend.pojo.to.SensorServiceApplyParamTo;
import com.yaohy.intelligentfarmingbackend.pojo.to.SensorServiceApplyTo;
import com.yaohy.intelligentfarmingbackend.pojo.to.SensorSleepServiceApplyParamTo;
import com.yaohy.intelligentfarmingbackend.service.UserService;
import com.yaohy.intelligentfarmingbackend.util.MessageUtil;
import com.yaohy.intelligentfarmingbackend.util.MinioUtil;
import com.yaohy.intelligentfarmingbackend.util.MqttUtil;
import com.yaohy.intelligentfarmingbackend.util.ml.CropRecommendTo;
import com.yaohy.intelligentfarmingbackend.util.ml.MlUtil;
import com.yaohy.intelligentfarmingbackend.util.request.RequestUtils;
import com.yaohy.intelligentfarmingbackend.util.request.dto.Param;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.StreamingHttpOutputMessage;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@SpringBootTest(classes = IntelligentFarmingBackEndApplication.class)
class IntelligentFarmingBackEndApplicationTests {

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
//    @Test
//    void contextLoads() {
//
//    }
//
//    @Test
//    void test(){
//        System.out.println(Integer.valueOf(60 * 60));
//    }
//
//    @Test
//    void testMessage(){
//        Boolean result = MessageUtil.send("15002232269", "abc123456");
//
//        System.out.println(result);
//    }
//
//    @Test
//    void testMinioDelete(){
//        MinioUtil.deleteJpg("/user/img/1.jpg");
//    }
//
//    @Test
//    void testVerificationCode(){
//        Random random = new Random();
//        StringBuilder verificationCode = new StringBuilder();
//
//
//        for (int i=0;i<6;i++){
//            verificationCode.append(random.nextInt(10));
//        }
//
//        System.out.println(verificationCode);
//    }

    @Test
    void array(){
        Object[] obj = new Object[1];
        List<Object> objects = new ArrayList<>();

        obj[0] = objects;
        objects.add("asdasdasdasdd");
//        RequestUtils.POST("https://www.baidu.com",new Param("obj", JSONUtil.toJsonStr(obj)));

        HashMap reqMap = new HashMap<String,Object[]>();
        reqMap.put("obj",obj);
        String result = restTemplate.getForObject("https://www.baidu.com", String.class, reqMap);
        System.out.println(result);

    }

    @Test
    void time(){
        System.out.println(new Date().getTime());
    }


    @Autowired
    private MqttUtil mqttUtil;
    @Test
    void testMqttUtilSend(){
        String objectId = "abcdefg";
        SensorServiceApplyTo sensorServiceApplyTo = new SensorServiceApplyTo();
        sensorServiceApplyTo.setName("sleep");

        SensorSleepServiceApplyParamTo sensorSleepServiceApplyParamTo = new SensorSleepServiceApplyParamTo();
        sensorSleepServiceApplyParamTo.setStartTime(new Date().getTime());
        sensorSleepServiceApplyParamTo.setStopTime(new Date().getTime());

        sensorServiceApplyTo.setParam(sensorSleepServiceApplyParamTo);

        mqttUtil.sendServiceApplyMessage(objectId,"test",sensorServiceApplyTo);
    }

    @Test
    void testMqttUtilReceive(){
        String objectId = "abcdefg";

        String message = mqttUtil.receiveServiceApplyMessage(objectId, "test");
        System.out.println("---message---");
        System.out.println(message);
    }

    @Test
    void testCropRecommend(){
        String N = "85";
        String P = "58";
        String K = "41";
        String temperature = "21.77046169";
        String humidity = "80.31964408";
        String ph = "7.038096361";
        String rainfall = "226.6555374";
        CropRecommendTo cropRecommendTo = MlUtil.cropRecommend(N, P, K, temperature, humidity, ph, rainfall);
        System.out.println(cropRecommendTo);
    }

}
