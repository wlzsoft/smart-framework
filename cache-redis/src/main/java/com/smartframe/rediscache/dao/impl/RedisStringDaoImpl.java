package com.smartframe.rediscache.dao.impl;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import com.smartframe.basics.exception.BusinessException;
import com.smartframe.rediscache.dao.RedisStringDao;

/**
 * 
 * <p><b>Title:</b><i>缓存dao,目前只针对 Session缓存</i></p>
 * <p>Desc: TODO</p>
 * <p>source folder:{@docRoot}</p>
 * @version Version 0.1
 *
 */
@Repository
public class RedisStringDaoImpl<T> implements RedisStringDao<T> {
	
	private static final long serialVersionUID = 7277091732315928217L;
	
	@Resource
    private RedisTemplate<String,T> redisTemplate;

    public RedisTemplate<String, T> getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate<String, T> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/** 
	 * 方法用途: 添加值
	 * 操作步骤: 通过ADD添加数据时不允许相同键值<br>
	 * @param key 保存键
	 * @param value 对象值
	 */
	@Override
	public void add(String key, T value) throws BusinessException {
		ValueOperations<String, T>  stringOperations  = redisTemplate.opsForValue();
		stringOperations.set(key, value);
	}
	
	/** 
	 * 方法用途: 添加值
	 * 操作步骤: 通过ADD添加数据时不允许相同键值<br>
	 * @param key 保存键
	 * @param export 超时时间
	 * @param value 对象值
	 */
	@Override
	public void add(String key, int export,  T value) throws BusinessException {
		ValueOperations<String, T>  stringOperations  = redisTemplate.opsForValue();
		stringOperations.set(key, value, export, TimeUnit.SECONDS);//秒
	}

	/** 
	 * 方法用途: 返回值
	 * 操作步骤: <br>
	 * @param key 保存键
	 * @return 缓存保存的对象
	 */
	@Override
	public Object get(String key) throws BusinessException {
		ValueOperations<String, T>  stringOperations  = redisTemplate.opsForValue();
		return stringOperations.get(key);
	}
	
	/** 
	 * 方法用途: 删除值
	 * 操作步骤: <br>
	 * @param key 保存键
	 * @return 删除成功为TRUE失败为FALSE
	 */
	@Override
	public void delete(String key) throws BusinessException{
		redisTemplate.delete(key);
	}
	

}
