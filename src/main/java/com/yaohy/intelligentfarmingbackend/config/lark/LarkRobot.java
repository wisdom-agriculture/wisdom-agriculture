package com.yaohy.intelligentfarmingbackend.config.lark;


import lombok.Data;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.yaohy.intelligentfarmingbackend.aop.WebLogAspect.getParameter;

@Data
@Component
@DependsOn("restTemplate")
@ConfigurationProperties(prefix = "lark")
public class LarkRobot {

    @Value("${lark.dev}")
    private String dev;

    @Value("${lark.url}")
    private String url;

    @Autowired
    private RestTemplate restTemplate;

    public LarkRobot(){

    }

    public LarkRobot(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public String context(String title, String time, String errorUri, String errorType, String errorMessage) {
        String exception1 = "{\"config\": {\"wide_screen_mode\": true },\"elements\": [ {\"tag\": \"markdown\", \"content\": \"**";
        String exception2 = "**\\n";
        String exception3 = "\" }, { \"tag\": \"div\", \"fields\": [ {\"is_short\": true, \"text\": { \"tag\": \"lark_md\",\"content\": \"**\uD83D\uDDF3异常来源：**\\n";
        String exception4 = "\" } },{ \"is_short\": true,\"text\": {\"tag\": \"lark_md\", \"content\": \"**\uD83D\uDCDD异常类型：**\\n";
        String exception5 = "\"}}]},{\"tag\": \"markdown\", \"content\": \"异常详情：\\n";
        String exception6 = "\"}]}";

        return exception1 + title + exception2 + time + exception3 + errorUri + exception4 + errorType + exception5 + errorMessage + exception6;
    }

    public synchronized void sendExceptionMessage(JoinPoint joinPoint, Exception exception) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        //获取当前请求对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();

        @Data
        class Request{
            private String uri;
            private String method;
            private Object parameter;
        }

        Request webRequest = new Request();
        webRequest.setMethod(request.getMethod());
        webRequest.setParameter(getParameter(method,joinPoint.getArgs()));
        webRequest.setUri(request.getRequestURI());

        String title = "服务异常报告";
        String message = context(title,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), request.getRequestURI(), exception.getClass().getName(), webRequest.toString());

        Map<String, String> map = new HashMap<>();
        map.put("card", message);
        map.put("msg_type", "interactive");
        restTemplate.postForObject(url, map, Object.class);

        map.clear();
        map.put("content", "{\"text\":\""+ exception.getMessage()+"\\n"+ Arrays.toString(exception.getStackTrace()) +"\"}");
        map.put("msg_type", "text");
        restTemplate.postForObject(url, map, Object.class);
    }

    public void finish() {
        if (dev.equals("true"))
            return;
        Map<String, String> map = new HashMap<>();
        map.put("content", "{\"text\":\"后端服务自启动完成 \\n操作人：聪明的机器人\"}");
        map.put("msg_type", "text");
        Object object = restTemplate.postForObject(url, map, Object.class);
        System.out.println("后端服务重启完成消息已发送，以下是返回内容");
        System.out.println(object);

    }

    public synchronized void alertException(Exception e) {
        if (dev.equals("true"))
            return;
//        Map<String, String> map = new HashMap<>();
//        System.out.println(e.getMessage());
//        System.out.println(Arrays.toString(e.getStackTrace()));
//        map.put("content", "{\"text\":\"后端服务出现不可拦截异常，原因如下\\n "+e.getMessage().replaceAll("\n","\\n")+"\\n"+ Arrays.toString(e.getStackTrace()).replaceAll("\n","\\n") +"\"}");
//        map.put("msg_type", "text");
//        Object object = restTemplate.postForObject(url, map, Object.class);


        String title = "服务异常报告";
        String message = context(title,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), "unknown", e.getClass().getName(), "unknown");

        Map<String, String> map = new HashMap<>();
        map.put("card", message);
        map.put("msg_type", "interactive");
        restTemplate.postForObject(url, map, Object.class);

        map.clear();
        map.put("content", "{\"text\":\""+ e.getMessage().replaceAll("\n","\\n")+"\\n"+ Arrays.toString(e.getStackTrace()).replaceAll("\n","\\n") +"\"}");
        map.put("msg_type", "text");
        restTemplate.postForObject(url, map, Object.class);

    }

    @PreDestroy
    public void end() {
//        return;
        if (dev.equals("true"))
            return;
        Map<String, String> map = new HashMap<>();
        map.put("content", "{\"text\":\"后端服务正在关闭 \\n操作人：聪明的机器人\"}");
        map.put("msg_type", "text");
        System.out.println(restTemplate);
        Object object = restTemplate.postForObject(url, map, Object.class);
        System.out.println("后端服务正在重启消息已发送，以下是返回内容");
        System.out.println(object);

    }

}

