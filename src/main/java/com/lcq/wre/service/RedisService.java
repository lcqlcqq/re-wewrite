package com.lcq.wre.service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis操作
 * 对象和数组都以json形式进行存储
 */
public interface RedisService {
    /**
     * 存储数据
     */
    void set(String key, String value, long time, TimeUnit timeUnit);

    /**
     * 获取数据
     */
    String get(String key);

    /**
     * 删除数据
     */
    Boolean remove(String key);

    /**
     * 批量删除
     */
    Long remove(Set<String> keys);

    /**
     * 自增操作
     * @param delta 自增步长
     */
    Long increment(String key, long delta);

    /**
     * 获取相应前缀的key
     * @param prefix
     * @return
     */
    Set<String> getKeys(String... prefix);

}
