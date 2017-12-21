package com.smartframe.rediscache.dao;

import java.io.Serializable;

import com.smartframe.basics.exception.BusinessException;

public interface IRedisDao extends Serializable {
	/** 
	 * 方法用途: 添加值
	 * 操作步骤: 通过ADD添加数据时不允许相同键值<br>
	 * @param key 保存键
	 * @param value 对象值
	 */
	public <T> T add(String key, T value) throws BusinessException;
	/** 
	 * 方法用途: 添加值
	 * 操作步骤: 通过ADD添加数据时不允许相同键值<br>
	 * @param key 保存键
	 * @param export 超时时间
	 * @param value 对象值
	 */	
	public <T> T add(String key,int export,T value) throws BusinessException;
	
	
	/** 
	 * 方法用途: 返回值
	 * 操作步骤: <br>
	 * @param key 保存键
	 * @return 缓存保存的对象
	 */
	public Object get(String key) throws BusinessException; 
	
	/** 
	 * 方法用途: 删除值
	 * 操作步骤: <br>
	 * @param key 保存键
	 * @return 删除成功为TRUE失败为FALSE
	 */
	public boolean delete(String key) throws BusinessException;
	
}
