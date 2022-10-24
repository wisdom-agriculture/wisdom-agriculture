package com.yaohy.intelligentfarmingbackend.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 写入String缓存
     * @param key 键
     * @param value 值
     * @return boolean
     */
    public boolean set(final String key,Object value){
        boolean result = false;
        try {
            ValueOperations<Serializable,Object> operations = redisTemplate.opsForValue();
            operations.set(key,value);
            result = true;
        } catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 设置缓存过期时间
     * @param key 键
     * @param value 值
     * @param expireTime 过期时间
     * @param timeUnit timeUnit
     * @return boolean
     */
    public boolean set(final String key, Object value, Long expireTime, TimeUnit timeUnit){
        boolean result = false;
        try {
            ValueOperations<Serializable,Object> operations = redisTemplate.opsForValue();
            operations.set(key,value);
            redisTemplate.expire(key,expireTime,timeUnit);
            result = true;
        } catch(Exception e){
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 判断缓存是否存在
     * @param key 键
     * @return boolean
     */
    public boolean exists(final String key){
        boolean flag = false;
        try {
            flag = redisTemplate.hasKey(key);
        } catch (Exception e){
//            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除缓存
     * @param key 键
     */
    public Boolean remove(final String key){
        if (exists(key)){
            return redisTemplate.delete(key);
        }
        return false;
    }

    /**
     * 取出缓存
     * @param key 键
     * @return 缓存
     */
    public Object get(final String key){
        Object result = null;
        ValueOperations<Serializable,Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);

        return result;
    }

    public boolean lPush(final String key,Object value){
        boolean result = false;

        try {
            redisTemplate.opsForList().leftPush(key,value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<Object> rPopAll(String key){
        Long size = redisTemplate.opsForList().size(key);
        if (size == null || size == 0) return null;
        List list = redisTemplate.opsForList().range(key, 0, size);
        redisTemplate.opsForList().trim(key,1,0);

        return list;
    }

    public synchronized Object rPop(String key){

        return redisTemplate.opsForList().rightPop(key);
    }

    public boolean sadd(String key,String element){
        return redisTemplate.opsForSet().add(key,element) == 1;
    }

    public Long ssize(String key){
        return redisTemplate.opsForSet().size(key);
    }
    public List<String> spopAll(String key){
        List<String> popList = redisTemplate.opsForSet().pop(key, redisTemplate.opsForSet().size(key));

        return popList;
    }



}
