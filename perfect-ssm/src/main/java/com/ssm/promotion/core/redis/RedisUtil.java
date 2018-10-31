package com.ssm.promotion.core.redis;

import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * redis util
 * @author chj
 * @date 2018-6-19
 *
 */
@Component
public class RedisUtil {

    private static final String CACHE_NAME = "perfect-ssm-cache:";
    // 过期时间
    private static final int EXPIRE_TIME;
    
    private RedisTemplate template;

    private RedisCache cache;
    
    static {
    	EXPIRE_TIME = 60 * 60 * 24;
    }
    
    public RedisUtil() {
        init(EXPIRE_TIME);
    }
    
    public RedisUtil(int exp) {
    	init(exp);
    }
    
    public void init(int expire) {
        template = SpringUtil.getBean("redisTemplate");//RedisCacheConfig中定义了
        cache = new RedisCache(CACHE_NAME, CACHE_NAME.getBytes(), template, expire);
    }

    public void put(String key, Object obj) {
        cache.put(key, obj);
    }

    public Object get(String key, Class clazz) {
        return cache.get(key) == null ? null : cache.get(key, clazz);
    }

    public void del(String key) {
        cache.evict(key);
    }
    
}