package com.smartframe.rediscache.dao.impl;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;

import com.smartframe.basics.exception.BusinessException;
import com.smartframe.rediscache.dao.IRedisDao;

public class RedisDaoImpl implements IRedisDao {
	
	private static final long serialVersionUID = 8641436510151219516L;
	
	private final Logger LOGGER = LoggerFactory.getLogger(RedisDaoImpl.class);
	
    @Resource
    private RedisTemplate<String,Object> redisTemplate;
    
	@Resource
	private CacheManager cacheManager;

	@Override
	@CachePut(value="session",key="#key",condition="#result != null")
	public <T> T add(String key, T value) throws BusinessException {
       return value;
	}

	@Override
	@CachePut(value="session",key="#key",condition="#result != null")
	public <T> T add(String key, int export, T value) throws BusinessException {
		if(export>0) {
			LOGGER.info("会话超时设置(默认30分钟)："+redisTemplate.expire(key, export,TimeUnit.SECONDS));
			LOGGER.info("会话过期时间倒计时(秒)："+redisTemplate.getExpire(key,TimeUnit.SECONDS));
		}
		return value;
	}

	@Override
	@Cacheable(value="session",key="#key")
	public Object get(String key) throws BusinessException {
		Cache cache = cacheManager.getCache("session");
		if(cache != null) {
			return cache.get(key);
		} 
		return null;
	}

	@Override
	@CacheEvict(value="session",key="#key")
	public boolean delete(String key) throws BusinessException {
		Cache cache = cacheManager.getCache("session");
		if(cache != null) {
			cache.evict(key);
		} 
		return true;
	}

	

}
