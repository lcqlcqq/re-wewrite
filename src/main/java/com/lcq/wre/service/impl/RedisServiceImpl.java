package com.lcq.wre.service.impl;

import com.lcq.wre.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis操作Service的实现类
 */
@Service
public class RedisServiceImpl implements RedisService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void set(String key, String value,long time,TimeUnit timeUnit) {
        stringRedisTemplate.opsForValue().set(key, value,time,timeUnit);
    }

    @Override
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public Boolean remove(String key) {
        return stringRedisTemplate.delete(key);
    }

    @Override
    public Long remove(Set<String> keys) {
        return stringRedisTemplate.delete(keys);
    }

    @Override
    public Long increment(String key, long delta) {
        return stringRedisTemplate.opsForValue().increment(key,delta);
    }

    @Override
    public Set<String> getKeys(String... prefix) {
        Set<String> keys = new HashSet<>();
        Set<String> ks;
        for(String pref : prefix){
            ks = stringRedisTemplate.keys(pref);
            if(ks!=null && ks.size() > 0)
                keys.addAll(ks);
        }
        return keys;
    }
}