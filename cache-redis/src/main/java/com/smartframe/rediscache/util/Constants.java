package com.smartframe.rediscache.util;

/**
 * 
 * <p><b>Title:</b><i>缓存时间常量</i></p>
 * <p>Desc: TODO</p>
 * <p>source folder:{@docRoot}</p>
 * @version Version 0.1
 *
 */
public class Constants {
	/**
	 * 缓存时效 5分钟钟
	 */
	public static final int CACHE_EXP_SENDCONDs = 60*5; 
	/** 
	 * 缓存时效 30分钟 
	 */  
	public static final int CACHE_EXP_MINUTES = 7200; 
	/** 
     * 缓存时效 1小时 
     */  
    public static final int CACHE_EXP_HOUR = 3600 * 1; 
	/** 
     * 缓存时效 1天 
     */  
    public static final int CACHE_EXP_DAY = 3600 * 24;  
  
    /** 
     * 缓存时效 1周 
     */  
    public static final int CACHE_EXP_WEEK = 3600 * 24 * 7;  
  
    /** 
     * 缓存时效 1月 
     */  
    public static final int CACHE_EXP_MONTH = 3600 * 24 * 30;  
  
    /** 
     * 缓存时效 永久 
     */  
    public static final int CACHE_EXP_FOREVER = 0;  
  
    /** 
     * 冲突延时 1秒 
     */  
    public static final int MUTEX_EXP = 1;  
    /** 
     * 冲突键 
     */  
    public static final String MUTEX_KEY_PREFIX = "MUTEX_";  
  
  
  
}

