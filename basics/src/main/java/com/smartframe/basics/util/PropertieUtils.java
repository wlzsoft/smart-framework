package com.smartframe.basics.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

public class PropertieUtils {

	private Properties props = new Properties();
	private InputStream jndiInput = null;

	public PropertieUtils(String fileName) {
		try {
			jndiInput = PropertieUtils.class.getClassLoader().getResourceAsStream(fileName);
			props.load(jndiInput);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getProperty(String name) {
		return props.getProperty(name);
	}
	
	public String getString(String name) {
		return props.getProperty(name);
	}
	
	public String getString(String name, String df) {
		return props.getProperty(name, df);
	}

	public Boolean getBoolean(String name) {
		String val = props.getProperty(name);
		return val == null ? null : Boolean.valueOf(val);
	}
	
	public Boolean getBoolean(String name, Boolean df) {
		String val = props.getProperty(name);
		return val == null ? df : Boolean.valueOf(val);
	}
	
	public Boolean getInteger(String name) {
		String val = props.getProperty(name);
		return val == null ? null : Boolean.valueOf(val);
	}
	
	public Integer getInteger(String name, Integer df) {
		String val = props.getProperty(name);
		return val == null ? df : DataUtil.parseInt(val);
	}
	
	public Object set(String key, String value) {
		return props.setProperty(key, value);
	}
	
	public void close() {
		try {
			jndiInput.close();
		} catch (IOException e) {
		}
	}
	
	/**
	 * 
	 * 方法用途: 获取所有属性名<br>
	 * 操作步骤: TODO<br>
	 * @return
	 */
	public Set<String> stringPropertyNames(){
		return props.stringPropertyNames();
	}
	
	public static void main(String[] args){
		PropertieUtils util = new PropertieUtils("redis-cfg.properties");
		Set<String> set = util.stringPropertyNames();
		for(Iterator<String> it = set.iterator();it.hasNext();){
			System.out.println(it.next());
		}
	}


}
