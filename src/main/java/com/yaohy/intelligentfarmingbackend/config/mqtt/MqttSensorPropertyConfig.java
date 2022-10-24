package com.yaohy.intelligentfarmingbackend.config.mqtt;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yaohy.intelligentfarmingbackend.mapper.SensorDataMapper;
import com.yaohy.intelligentfarmingbackend.mapper.SensorMapper;
import com.yaohy.intelligentfarmingbackend.pojo.domain.Sensor;
import com.yaohy.intelligentfarmingbackend.pojo.domain.SensorData;
import com.yaohy.intelligentfarmingbackend.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import java.util.Objects;

@Slf4j
@Configuration
public class MqttSensorPropertyConfig {

    @Value("${mqtt.sensor.property.url}")
    private String url;

    @Value("${mqtt.sensor.property.clientId}")
    private String clientId;

    @Value("${mqtt.sensor.property.topic}")
    private String topic;

    @Value("${mqtt.sensor.property.username:}")
    private String username;

    @Value("${mqtt.sensor.property.password:}")
    private String password;


    @Value("${mqtt.sensor.property.timeout}")
    private Integer timeout;

    @Value("${mqtt.sensor.property.qos}")
    private Integer qos;

    @Autowired
    private RedisUtil redisUtil;

    @Bean
    public MessageChannel mqttSensorPropertyInputChannel(){

        return new DirectChannel();
    }

    @Autowired
    private SensorMapper sensorMapper;
    @Autowired
    private SensorDataMapper sensorDataMapper;

    @Bean(name = "SensorPropertyMessageProducer")
    public MessageProducer inbound(){
        DefaultMqttPahoClientFactory mqttPahoClientFactory = new DefaultMqttPahoClientFactory();

        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();

        if (Strings.isEmpty(username) || Strings.isEmpty(password)) log.info("--------mqtt正在使用匿名连接--------");
        else {
            mqttConnectOptions.setUserName(username);
            mqttConnectOptions.setPassword(password.toCharArray());
            mqttConnectOptions.setConnectionTimeout(timeout);
            mqttConnectOptions.setAutomaticReconnect(true);
            //不清除session 可以获取未接收到的信息
//            mqttConnectOptions.setCleanSession(false);
//            mqttConnectOptions.set
        }

        mqttPahoClientFactory.setConnectionOptions(mqttConnectOptions);

        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(
                url,clientId,mqttPahoClientFactory
        );

//        adapter.setDisconnectCompletionTimeout(timeout);
//        adapter.setCompletionTimeout(timeout);
//        adapter.setSendTimeout(timeout);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(qos);
        adapter.setOutputChannel(mqttSensorPropertyInputChannel());
        adapter.addTopic(topic);

        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttSensorPropertyInputChannel")
    public MessageHandler PropertyMessageHandler(){
        return new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                log.info(message.getPayload().toString());
                log.info(message.getHeaders().toString());
//                redisUtil.lPush("mqtt_message",message.getPayload());

                String topic = (String) message.getHeaders().get("mqtt_receivedTopic");
                String objectId = topic.split("/")[1];

                Sensor sensor = sensorMapper.selectOne(new QueryWrapper<Sensor>().eq("object_id", objectId));
                if (Objects.isNull(sensor)) return;

                SensorData sensorData = new SensorData();
                sensorData.setSensorId(sensor.getId());
                sensorData.setContent(JSONUtil.toJsonStr(message.getPayload()));
                sensorData.setTime(JSONUtil.parseObj(message.getPayload()).getLong("time"));
                redisUtil.set(sensor.getId().toString()+"-property",JSONUtil.toJsonStr(message.getPayload()));
                sensorDataMapper.insert(sensorData);
            }
        };
    }



    //TODO analyse other topics

}
