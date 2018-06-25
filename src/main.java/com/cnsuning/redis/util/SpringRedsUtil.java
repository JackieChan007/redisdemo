package com.cnsuning.redis.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;
@Service
public class SpringRedsUtil {

    private RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
//    public void  setRedisTemplate(RedisTemplate<String,String> redisTemplate){
//        this.redisTemplate =redisTemplate;
//    }

    /**
     * 指定失效时间
     *
     * @param key
     * @param time
     * @return
     */
    public boolean isExpire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public long getExprieTime(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    public void putCache(String key, String value) {
        boolean result = false;
        try {
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Autowired
    private RedisTemplate<String, String> template;

    // inject the template as ListOperations
    @Resource(name = "redisTemplate")
    private ListOperations<String, String> listOps;

    public void addLink(String userId, String url) {
        listOps.leftPush(userId, url);

    }
}
