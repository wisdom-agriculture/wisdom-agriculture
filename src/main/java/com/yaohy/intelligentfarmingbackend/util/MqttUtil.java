package com.yaohy.intelligentfarmingbackend.util;

import cn.hutool.json.JSONUtil;
import com.yaohy.intelligentfarmingbackend.pojo.to.SensorServiceApplyTo;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Objects;

@Component
public class MqttUtil {

    @Value("${mqtt.util.url}")
    private String url;

    @Value("${mqtt.util.username:}")
    private String username;

    @Value("${mqtt.util.clientId}")
    private String clientId;

    @Value("${mqtt.util.password:}")
    private String password;


    @Value("${mqtt.util.timeout}")
    private Integer timeout;

    @Value("${mqtt.util.qos}")
    private Integer qos;

    private final LinkedList<String> messageList = new LinkedList<>();

    public synchronized void sendServiceApplyMessage(String objectId,
                                               String topicSuffix,
                                               SensorServiceApplyTo sensorServiceApplyTo){

        try {
            MqttClient mqttClient = new MqttClient(url,clientId+"MqttUtil");
            MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
            if (Objects.isNull(username)) mqttConnectOptions.setUserName(username);
            if (Objects.isNull(password)) mqttConnectOptions.setPassword(password.toCharArray());
            mqttConnectOptions.setConnectionTimeout(timeout);

            //todo set callback
            mqttClient.connect(mqttConnectOptions);

            String topic = "obj/"+objectId+"/"+topicSuffix;

            //send message
            mqttClient.publish(topic, JSONUtil.toJsonStr(sensorServiceApplyTo).getBytes(),qos,false);
            mqttClient.close(false);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public synchronized String receiveServiceApplyMessage(String objectId,
                                               String topicSuffix){
        String message = null;

        try {
            MqttClient mqttClient = new MqttClient(url,clientId+"MqttUtil");
            MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
            if (Objects.isNull(username)) mqttConnectOptions.setUserName(username);
            if (Objects.isNull(password)) mqttConnectOptions.setPassword(password.toCharArray());
            mqttConnectOptions.setConnectionTimeout(timeout);

            //todo set callback
            mqttClient.connect(mqttConnectOptions);

            //receive message
            String topic = "obj/"+objectId+"/"+topicSuffix;

            mqttClient.subscribe(topic,qos,iMqttMessageListener());

            //wait 30s to receive message,if not close connection and note as time out
//            Thread.sleep(30000);

            //wait for message to arrive
            //solve always waiting
            long start = System.currentTimeMillis();

            while (messageList.isEmpty()){
                Thread.sleep(1);

                //time out
                long now = System.currentTimeMillis();
                if (now - start >= 30000) break;
            }

            message = messageList.poll();

            //need async acknowledge
//            mqttClient.close();
            mqttClient.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }

    public IMqttMessageListener iMqttMessageListener(){
        return new IMqttMessageListener() {
            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
//                System.out.println("topic: "+topic+" just received message");
//                System.out.println("message is "+message);
                messageList.offer(message.toString());
            }
        };
    }

}


