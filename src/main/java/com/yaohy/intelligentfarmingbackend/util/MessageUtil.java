package com.yaohy.intelligentfarmingbackend.util;

import okhttp3.*;

import java.io.IOException;
import java.util.Objects;

public class MessageUtil {

    private static final String username = "";
    private static final String password = "";
    private static String url = "";
    private static String message = "【智慧农业】你好，你的验证码是%s，24小时内有效，请勿频繁请求，若非本人操作，请无视此消息";

    private MessageUtil (){}

    public static Boolean send(String phone,String code){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(String.format(url,username,password,phone,encodeUrlString(String.format(message,code),"UTF-8"))).build();

        System.out.println(request.url());
        System.out.println(url);
        System.out.println(message);

        String result = "";
        try (Response response = okHttpClient.newCall(request).execute()){
            result = response.body().string();
            System.out.println(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Objects.equals(result,"0");
    }

    public static String encodeUrlString(String str, String charset) {
        String strret = null;
        if (str == null)
            return str;
        try {
            strret = java.net.URLEncoder.encode(str, charset);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return strret;
    }


}
